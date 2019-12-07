package net.ramify.model.record.xml;

import com.google.common.collect.Collections2;
import com.google.common.collect.Multimap;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.Record;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.provider.RecordsByPlaceIndex;
import net.ramify.model.record.provider.RecordsProvider;
import net.ramify.model.record.xml.collection.XmlRecordSets;
import net.ramify.utils.collections.MultimapCollectors;
import net.ramify.utils.file.FileUtils;
import net.ramify.utils.objects.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@XmlTransient
@Singleton
class XmlRecordProvider implements RecordsProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlRecordProvider.class);

    private final Map<RecordSetId, File> xmlFiles;
    private final JAXBContext context;
    private final RecordContext recordContext;
    private PlaceIndex placeIndex;
    private final RecordSetProvider recordSetProvider;

    XmlRecordProvider(
            final Map<RecordSetId, File> xmlFiles,
            final JAXBContext context,
            final RecordContext recordContext,
            final RecordSetProvider recordSetProvider) {
        this.xmlFiles = xmlFiles;
        this.context = context;
        this.recordContext = recordContext;
        this.recordSetProvider = recordSetProvider;
    }

    @CheckForNull
    @Override
    public Records get(final RecordSetId id) {
        return Functions.ifNonNull(xmlFiles.get(id), file -> this.unmarshal(file, id));
    }

    private Records unmarshal(final File file, final RecordSetId id) {
        FileUtils.checkReadableFile(file);
        logger.info("Reading records for {} in {}", id, file);
        try {
            final var recordSets = (XmlRecordSets) context.createUnmarshaller().unmarshal(file);
            final var records = recordSets.records(id, recordContext, recordSetProvider);
            return Records.of(records);
        } catch (final Exception ex) {
            throw new RuntimeException("Error reading records for " + id + " from " + file, ex);
        }
    }

    @Nonnull
    @Override
    public Collection<Records> all() {
        return Collections.unmodifiableCollection(Collections2.transform(xmlFiles.keySet(), this::get));
    }

    @Nonnull
    @Override
    public synchronized RecordsByPlaceIndex placeIndex() {
        return placeIndex == null ? placeIndex = this.index() : placeIndex;
    }

    private PlaceIndex index() {
        logger.info("Indexing record places ...");
        final Multimap<PlaceId, RecordSetId> index = xmlFiles.keySet()
                .stream()
                .map(this::get)
                .flatMap(Records::records)
                .flatMap(XmlRecordProvider::streamRecordPlaces)
                .parallel()
                .collect(MultimapCollectors.collector(Map.Entry::getKey, Map.Entry::getValue));
        logger.info("Indexed {} record places.", index.size());
        return new PlaceIndex(index);
    }

    private static Stream<Map.Entry<PlaceId, RecordSetId>> streamRecordPlaces(final Record record) {
        final var recordSetId = record.recordSetId();
        return record.placeIds().stream().map(id -> new AbstractMap.SimpleImmutableEntry<>(id, recordSetId));
    }

    @XmlTransient
    private class PlaceIndex implements RecordsByPlaceIndex {

        private final Multimap<PlaceId, RecordSetId> index;

        PlaceIndex(final Multimap<PlaceId, RecordSetId> index) {
            this.index = index;
        }

        @CheckForNull
        @Override
        public Records get(final PlaceId id) {
            final var ids = index.get(id);
            if (ids.isEmpty()) return Records.none();
            return () -> ids.stream()
                    .map(XmlRecordProvider.this::get)
                    .filter(Objects::nonNull)
                    .flatMap(Records::records)
                    .filter(record -> record.hasPlace(id));
        }

    }

}
