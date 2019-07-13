package net.ramify.model.record.xml;

import net.ramify.model.date.parse.DateParser;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.provider.RecordsProvider;
import net.ramify.utils.objects.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckForNull;
import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.Map;

@XmlTransient
@Singleton
class XmlRecordProvider implements RecordsProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlRecordProvider.class);

    private final Map<RecordSetId, File> xmlFiles;
    private final NameParser nameParser;
    private final DateParser dateParser;

    XmlRecordProvider(final Map<RecordSetId, File> xmlFiles, final NameParser nameParser, final DateParser dateParser) {
        this.xmlFiles = xmlFiles;
        this.nameParser = nameParser;
        this.dateParser = dateParser;
    }

    @CheckForNull
    @Override
    public Records get(final RecordSetId key) {
        return Functions.ifNonNull(xmlFiles.get(key), this::unmarshal);
    }

    private Records unmarshal(final File file) {
        throw new UnsupportedOperationException();
    }

    void add(final RecordSetId id, final File file) {
        xmlFiles.put(id, file);
    }

}
