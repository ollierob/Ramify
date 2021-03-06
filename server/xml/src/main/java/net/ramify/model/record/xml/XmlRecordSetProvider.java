package net.ramify.model.record.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.record.collection.DelegatedRecordSet;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.xml.collection.DefaultRecordSet;
import net.ramify.model.record.xml.collection.XmlRecordSets;
import net.ramify.utils.collections.MapUtils;
import net.ramify.utils.file.FileTraverseUtils;
import net.ramify.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@XmlTransient
class XmlRecordSetProvider extends AbstractMappedProvider<RecordSetId, RecordSet> implements RecordSetProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlRecordSetProvider.class);

    private final XmlRecordSetRelativesProvider relatives;
    private final Map<RecordSetId, File> recordSetFiles = Maps.newHashMap();

    XmlRecordSetProvider(final Map<RecordSetId, RecordSet> recordSets, final XmlRecordSetRelativesProvider relatives) {
        super(recordSets);
        this.relatives = relatives;
    }

    void addAll(final File file, final Collection<DefaultRecordSet> recordSets) {
        super.putAll(MapUtils.toMapLastKey(recordSets, HasRecordSetId::recordSetId));
        recordSets.forEach(relatives::add);
        recordSets.forEach(rs -> recordSetFiles.put(rs.recordSetId(), file));
    }

    @Nonnull
    @Override
    public Set<RecordSet> matching(
            final Predicate<? super RecordSet> predicate,
            final int limit,
            final boolean onlyParents) {

        final var keys = this.keys();

        if (!onlyParents) return keys.stream()
                .map(this::get)
                .filter(predicate)
                .limit(limit)
                .collect(Collectors.toSet());

        final var matching = Maps.<RecordSetId, RecordSet>newHashMap();

        for (final var key : keys) {

            if (matching.containsKey(key)) continue;
            final var record = this.get(key);
            if (record == null || !predicate.test(record)) continue;

            final var match = this.highestMatchingParent(record, predicate);
            matching.put(match.recordSetId(), match);

            if (matching.size() >= limit) break;

        }

        return Sets.newHashSet(matching.values());

    }

    @Nonnull
    RecordSet highestMatchingParent(RecordSet recordSet, final Predicate<? super RecordSet> predicate) {
        var childId = recordSet.recordSetId();
        RecordSetId parentId;
        while ((parentId = relatives.parentId(childId)) != null) {
            final var candidate = this.get(parentId);
            if (!predicate.test(candidate)) break;
            recordSet = candidate;
            childId = parentId;
        }
        return recordSet;
    }

    Map<RecordSetId, File> recordSetFiles() {
        return recordSetFiles;
    }

    @Nonnull
    RecordSetProvider immutable() {
        final var records = this.mutableMap();
        records.keySet().forEach(id -> this.updateParentCounts(id, records));
        return new XmlRecordSetProvider(ImmutableMap.copyOf(records), relatives);
    }

    private void updateParentCounts(final RecordSetId id, final Map<RecordSetId, RecordSet> records) {
        final var record = this.get(id);
        if (record == null || record.numRecords() == 0) return; //Ignore no records
        if (relatives.hasChildren(id)) return; //Ignore non-leaf nodes
        var parentId = record.recordSetId();
        while ((parentId = relatives.parentId(parentId)) != null) {
            records.compute(parentId, (i, parent) -> ResizedRecordSet.of(parent, record));
        }
    }

    static XmlRecordSetProvider readRecordsInDirectory(
            final JAXBContext jaxbContext,
            final File root,
            final XmlRecordSetRelativesProvider relatives,
            final RecordContext recordContext) throws JAXBException {
        final var provider = new XmlRecordSetProvider(Maps.newLinkedHashMap(), relatives);
        final var unmarshaller = jaxbContext.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(
                root,
                file -> file.getName().endsWith(".xml") && file.getPath().contains("record"),
                file -> readRecordsInFile(unmarshaller, file, provider, recordContext));
        logger.info("Loaded {} record sets from {}.", provider.size(), root);
        return provider;
    }

    private static void readRecordsInFile(final Unmarshaller unmarshaller, final File file, final XmlRecordSetProvider setProvider, final RecordContext context) {
        FileUtils.checkReadableFile(file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading records from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlRecordSets)) return;
            final var records = (XmlRecordSets) unmarshalled;
            final var recordSets = records.recordSets(setProvider, context);
            setProvider.addAll(file, recordSets);
        } catch (final JAXBException jex) {
            logger.warn("Could not read records in file " + file + ": " + jex.getMessage());
        } catch (final Exception ex) {
            throw new RuntimeException("Error reading records in file " + file, ex);
        }
    }

    private static class ResizedRecordSet extends DelegatedRecordSet {

        static ResizedRecordSet of(final RecordSet parent, final RecordSet leaf) {
            if (parent instanceof ResizedRecordSet) {
                //Flatten
                final var resized = (ResizedRecordSet) parent;
                return new ResizedRecordSet(resized.delegate(), leaf.numRecords() + resized.numRecords(), leaf.numIndividuals() + resized.numIndividuals());
            }
            return new ResizedRecordSet(parent, leaf.numRecords(), leaf.numIndividuals());
        }

        private final int childRecords;
        private final int childIndividuals;

        private ResizedRecordSet(final RecordSet delegate, final int childRecords, final int childIndividuals) {
            super(delegate);
            this.childRecords = childRecords;
            this.childIndividuals = childIndividuals;
        }

        @Override
        public int numRecords() {
            return super.numRecords() + childRecords;
        }

        @Override
        public int numIndividuals() {
            return super.numIndividuals() + childIndividuals;
        }

        @Nonnull
        @Override
        public RecordProto.RecordSet.Builder toProtoBuilder() {
            return super.toProtoBuilder()
                    .setNumIndividuals(this.numIndividuals())
                    .setNumRecords(this.numRecords());
        }

    }

}
