package net.ramify.model.record.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.ResizedRecordSet;
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

    XmlRecordSetProvider(final Map<RecordSetId, RecordSet> recordSets, final XmlRecordSetRelativesProvider relatives) {
        super(recordSets);
        this.relatives = relatives;
    }

    void addAll(final Collection<DefaultRecordSet> recordSets) {
        super.putAll(MapUtils.toMapLastKey(recordSets, HasRecordSetId::recordSetId));
        recordSets.forEach(relatives::add);
    }

    @Nonnull
    @Override
    public Set<RecordSet> matching(final Predicate<? super RecordSet> predicate, final int limit, final boolean onlyParents) {

        final var keys = this.keys();

        if (!onlyParents) return keys.stream()
                .map(this::get)
                .filter(predicate)
                .limit(limit)
                .collect(Collectors.toSet());

        final var matching = Maps.<RecordSetId, RecordSet>newHashMap();

        for (final var key : keys) {

            final var record = this.get(key);
            if (record == null) continue;
            if (!predicate.test(record)) continue;
            if (relatives.containsAnyParent(matching.keySet(), record.recordSetId())) continue;

            matching.put(record.recordSetId(), record);
            if (matching.size() >= limit) break;

        }

        return Sets.newHashSet(matching.values());

    }

    @Override
    protected ImmutableMap<RecordSetId, RecordSet> immutableMap() {
        return ImmutableMap.copyOf(Maps.transformValues(this.map(), set -> new ResizedRecordSet(set, this.determineSize(set))));
    }

    private int determineSize(final RecordSet set) {
        final var size = this.require(set.recordSetId()).size();
        final var childSize = relatives.allChildren(set).stream().mapToInt(RecordSet::size).sum();
        return size + childSize;
    }

    @Nonnull
    RecordSetProvider immutable() {
        return new XmlRecordSetProvider(this.immutableMap(), relatives);
    }

    static RecordSetProvider readRecordsInDirectory(
            final JAXBContext jaxbContext,
            final File root,
            final XmlRecordProvider recordProvider,
            final XmlRecordSetRelativesProvider relatives,
            final RecordContext recordContext) throws JAXBException {
        final var provider = new XmlRecordSetProvider(Maps.newLinkedHashMap(), relatives);
        final var unmarshaller = jaxbContext.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(
                root,
                file -> file.getName().endsWith(".xml") && file.getPath().contains("record"),
                file -> readRecordsInFile(unmarshaller, file, provider, recordProvider, recordContext));
        logger.info("Loaded {} record sets from {}.", provider.size(), root);
        return provider.immutable();
    }

    private static void readRecordsInFile(final Unmarshaller unmarshaller, final File file, final XmlRecordSetProvider provider, final XmlRecordProvider recordProvider, final RecordContext context) {
        FileUtils.checkReadableFile(file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading records from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlRecordSets)) return;
            final var records = (XmlRecordSets) unmarshalled;
            final var recordSets = records.recordSets(provider, context);
            provider.addAll(recordSets);
            recordSets.forEach(rs -> recordProvider.add(rs.recordSetId(), file));
        } catch (final JAXBException jex) {
            logger.warn("Could not read records in file " + file + ": " + jex.getMessage());
        } catch (final Exception ex) {
            throw new RuntimeException("Error reading records in file " + file, ex);
        }
    }

}
