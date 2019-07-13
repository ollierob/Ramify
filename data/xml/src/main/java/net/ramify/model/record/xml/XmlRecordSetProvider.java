package net.ramify.model.record.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.xml.collection.XmlRecordSets;
import net.ramify.utils.collections.MapUtils;
import net.ramify.utils.file.FileTraverseUtils;
import net.ramify.utils.file.FileUtils;
import net.ramify.utils.objects.Consumers;
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

    private final SetMultimap<RecordSetId, RecordSet> parentChildren;

    XmlRecordSetProvider(final Map<RecordSetId, RecordSet> recordSets, final SetMultimap<RecordSetId, RecordSet> parentChildren) {
        super(recordSets);
        this.parentChildren = parentChildren;
    }

    void addAll(final Collection<RecordSet> recordSets) {
        super.putAll(MapUtils.toMapLastKey(recordSets, HasRecordSetId::recordSetId));
        recordSets.forEach(rs -> Consumers.ifNonNull(rs.parent(), parent -> parentChildren.put(parent.recordSetId(), rs)));
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
        return Collections.unmodifiableSet(parentChildren.get(parentId));
    }

    @Nonnull
    RecordSetProvider immutable() {
        return new XmlRecordSetProvider(this.immutableMap(), ImmutableSetMultimap.copyOf(parentChildren));
    }

    static RecordSetProvider readRecordsInDirectory(final JAXBContext context, final File root, final DateParser dateParser) throws JAXBException {
        final var provider = new XmlRecordSetProvider(Maps.newConcurrentMap(), HashMultimap.create());
        final var unmarshaller = context.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(root, file -> file.getName().endsWith(".xml") && file.getName().contains("record"), file -> readRecordsInFile(unmarshaller, file, dateParser, provider));
        logger.info("Loaded {} record sets from {}.", provider.size(), root);
        return provider.immutable();
    }

    private static void readRecordsInFile(final Unmarshaller unmarshaller, final File file, final DateParser dateParser, final XmlRecordSetProvider provider) {
        FileUtils.checkReadableFile(file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading records from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlRecordSets)) return;
            final var records = (XmlRecordSets) unmarshalled;
            provider.addAll(records.recordSets(provider, dateParser));
        } catch (final JAXBException jex) {
            logger.warn("Could not read records in file " + file + ": " + jex.getMessage());
        }
    }

}
