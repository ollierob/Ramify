package net.ramify.model.record.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.xml.collection.XmlRecordSets;
import net.ramify.utils.collections.MapUtils;
import net.ramify.utils.file.FileTraverseUtils;
import net.ramify.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.Collection;
import java.util.Map;

@XmlTransient
class XmlRecordSetProvider extends AbstractMappedProvider<RecordSetId, RecordSet> implements RecordSetProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlRecordSetProvider.class);

    XmlRecordSetProvider(final Map<RecordSetId, RecordSet> recordSets) {
        super(recordSets);
    }

    void addAll(final Collection<RecordSet> recordSets) {
        super.putAll(MapUtils.toMapLastKey(recordSets, HasRecordSetId::recordSetId));
    }

    static RecordSetProvider readRecordsInDirectory(final JAXBContext context, final File root) throws JAXBException {
        final var provider = new XmlRecordSetProvider(Maps.newConcurrentMap());
        final var unmarshaller = context.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(root, file -> file.getName().endsWith(".xml") && file.getName().contains("record"), file -> readRecordsInFile(unmarshaller, file, provider));
        logger.info("Loaded {} record sets from {}.", provider.size(), root);
        return new XmlRecordSetProvider(provider.immutableMap());
    }

    private static void readRecordsInFile(final Unmarshaller unmarshaller, final File file, final XmlRecordSetProvider provider) {
        FileUtils.checkReadableFile(file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading records from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlRecordSets)) return;
            final var records = (XmlRecordSets) unmarshalled;
            provider.addAll(records.recordSets());
        } catch (final JAXBException jex) {
            logger.warn("Could not read records in file " + file + ": " + jex.getMessage());
        }
    }

}
