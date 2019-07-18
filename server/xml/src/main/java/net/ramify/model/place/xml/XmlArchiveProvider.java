package net.ramify.model.place.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.place.xml.archives.XmlArchives;
import net.ramify.model.record.archive.Archive;
import net.ramify.model.record.archive.ArchiveId;
import net.ramify.model.record.archive.ArchiveProvider;
import net.ramify.utils.file.FileTraverseUtils;
import net.ramify.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collection;
import java.util.Map;

public class XmlArchiveProvider extends AbstractMappedProvider<ArchiveId, Archive> implements ArchiveProvider {

    private static final Logger logger = LoggerFactory.getLogger(XmlArchiveProvider.class);

    XmlArchiveProvider(final Map<ArchiveId, Archive> map) {
        super(map);
    }

    void addAll(final Collection<Archive> archives) {
        archives.forEach(archive -> this.put(archive.archiveId(), archive));
    }

    ArchiveProvider immutable() {
        return new XmlArchiveProvider(this.immutableMap());
    }

    static ArchiveProvider readArchivesInDirectory(final JAXBContext context, final File root) throws JAXBException {
        final var provider = new XmlArchiveProvider(Maps.newHashMap());
        final var unmarshaller = context.createUnmarshaller();
        FileTraverseUtils.traverseSubdirectories(root, file -> file.getName().endsWith(".xml"), file -> readArchivesInFile(unmarshaller, file, provider));
        logger.info("Loaded {} places from {}.", provider.size(), root);
        return provider.immutable();
    }

    private static void readArchivesInFile(final Unmarshaller unmarshaller, final File file, final XmlArchiveProvider provider) {
        FileUtils.checkReadableFile(file);
        Preconditions.checkArgument(file.getName().endsWith(".xml"), "Not an XML file: %s", file);
        try {
            logger.info("Reading places from file {}", file);
            final var unmarshalled = unmarshaller.unmarshal(file);
            if (!(unmarshalled instanceof XmlArchives)) return;
            final var archives = (XmlArchives) unmarshalled;
            provider.addAll(archives.buildArchives());
        } catch (final JAXBException jex) {
            logger.warn("Could not read places in file " + file + ": " + jex.getMessage());
        }
    }

}
