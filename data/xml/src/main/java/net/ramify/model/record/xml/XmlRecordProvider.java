package net.ramify.model.record.xml;

import net.ramify.model.date.parse.DateParser;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.provider.RecordsProvider;
import net.ramify.model.record.xml.collection.XmlRecordSets;
import net.ramify.utils.file.FileUtils;
import net.ramify.utils.objects.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckForNull;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.Map;

@XmlTransient
@Singleton
class XmlRecordProvider implements RecordsProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlRecordProvider.class);

    private final Map<RecordSetId, File> xmlFiles;
    private final JAXBContext context;
    private final NameParser nameParser;
    private final DateParser dateParser;

    XmlRecordProvider(
            final Map<RecordSetId, File> xmlFiles,
            final JAXBContext context,
            final NameParser nameParser,
            final DateParser dateParser) {
        this.xmlFiles = xmlFiles;
        this.context = context;
        this.nameParser = nameParser;
        this.dateParser = dateParser;
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
            final var records = recordSets.records(id, dateParser, nameParser);
            return Records.of(records);
        } catch (final Exception ex) {
            throw new RuntimeException("Error reading records from " + file, ex);
        }
    }

    void add(final RecordSetId id, final File file) {
        xmlFiles.put(id, file);
    }

}
