package net.ramify.model.record.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.ResizedRecordSet;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.xml.collection.XmlRecordSets;
import net.ramify.utils.collections.MapUtils;
import net.ramify.utils.collections.SetUtils;
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
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@XmlTransient
class XmlRecordSetProvider extends AbstractMappedProvider<RecordSetId, RecordSet> implements RecordSetProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlRecordSetProvider.class);

    private final SetMultimap<RecordSetId, RecordSetId> parentChildren;

    XmlRecordSetProvider(
            final Map<RecordSetId, RecordSet> recordSets,
            final SetMultimap<RecordSetId, RecordSetId> parentChildren) {
        super(recordSets);
        this.parentChildren = parentChildren;
    }

    void addAll(final Collection<RecordSet> recordSets) {
        super.putAll(MapUtils.toMapLastKey(recordSets, HasRecordSetId::recordSetId));
        recordSets.forEach(this::addParent);
    }

    private void addParent(final RecordSet recordSet) {
        final var parent = recordSet.parent();
        if (parent == null) return;
        parentChildren.put(parent.recordSetId(), recordSet.recordSetId());
        this.addParent(parent);
    }

    @Nonnull
    @Override
    public Set<RecordSet> matching(final Predicate<? super RecordSet> predicate, final int limit) {
        return this.keys()
                .stream()
                .limit(limit)
                .map(this::get)
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Set<RecordSet> children(final RecordSetId parentId) {
        return Collections.unmodifiableSet(SetUtils.transform(parentChildren.get(parentId), this::require));
    }

    private Set<RecordSet> allChildren(final RecordSetId parentId) {
        final var childIds = parentChildren.get(parentId);
        if (childIds.isEmpty()) return Collections.emptySet();
        final var set = Sets.<RecordSet>newHashSet();
        childIds.forEach(childId -> {
            set.add(this.require(childId));
            set.addAll(this.allChildren(childId));
        });
        return set;
    }

    @Override
    protected ImmutableMap<RecordSetId, RecordSet> immutableMap() {
        return ImmutableMap.copyOf(Maps.transformValues(this.map(), set -> new ResizedRecordSet(set, this.determineSize(set.recordSetId()))));
    }

    private int determineSize(final RecordSetId id) {
        final var size = this.require(id).size();
        final var childSize = this.allChildren(id).stream().mapToInt(RecordSet::size).sum();
        return size + childSize;
    }

    @Nonnull
    RecordSetProvider immutable() {
        return new XmlRecordSetProvider(this.immutableMap(), ImmutableSetMultimap.copyOf(parentChildren));
    }

    static RecordSetProvider readRecordsInDirectory(
            final JAXBContext context,
            final File root,
            final DateParser dateParser,
            final XmlRecordProvider recordProvider) throws JAXBException {
        final var provider = new XmlRecordSetProvider(Maps.newConcurrentMap(), HashMultimap.create());
        final var unmarshaller = context.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(
                root,
                file -> file.getName().endsWith(".xml") && file.getPath().contains("record"),
                file -> readRecordsInFile(unmarshaller, file, dateParser, provider, recordProvider));
        logger.info("Loaded {} record sets from {}.", provider.size(), root);
        return provider.immutable();
    }

    private static void readRecordsInFile(final Unmarshaller unmarshaller, final File file, final DateParser dateParser, final XmlRecordSetProvider provider, final XmlRecordProvider recordProvider) {
        FileUtils.checkReadableFile(file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading records from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlRecordSets)) return;
            final var records = (XmlRecordSets) unmarshalled;
            final var recordSets = records.recordSets(provider, dateParser);
            provider.addAll(recordSets);
            recordSets.forEach(rs -> recordProvider.add(rs.recordSetId(), file));
        } catch (final JAXBException jex) {
            logger.warn("Could not read records in file " + file + ": " + jex.getMessage());
        } catch (final Exception ex) {
            throw new RuntimeException("Error reading records in file " + file, ex);
        }
    }

}
