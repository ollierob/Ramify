package net.ramify.model.record.xml;

import com.google.common.collect.Collections2;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.provider.RecordsProvider;
import net.ramify.model.record.xml.collection.XmlRecordSets;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@XmlTransient
@Singleton
class XmlRecordProvider implements RecordsProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlRecordProvider.class);

    private final Map<RecordSetId, File> xmlFiles;
    private final JAXBContext context;
    private final PlaceProvider places;
    private final RecordContext recordContext;

    XmlRecordProvider(
            final Map<RecordSetId, File> xmlFiles,
            final JAXBContext context,
            final PlaceProvider places,
            final RecordContext recordContext) {
        this.xmlFiles = xmlFiles;
        this.context = context;
        this.places = places;
        this.recordContext = recordContext;
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
            final var records = recordSets.records(id, recordContext);
            return Records.of(records);
        } catch (final Exception ex) {
            throw new RuntimeException("Error reading records for " + id + " from " + file, ex);
        }
    }

    void add(final RecordSetId id, final File file) {
        xmlFiles.put(id, file);
    }

    @Nonnull
    @Override
    public Collection<Records> all() {
        return Collections.unmodifiableCollection(Collections2.transform(xmlFiles.keySet(), this::get));
    }
}
