package net.ramify.model.place.xml;

import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.record.archive.Archive;
import net.ramify.model.record.archive.ArchiveId;
import net.ramify.model.record.archive.ArchiveProvider;

import java.util.Map;

public class XmlArchiveProvider extends AbstractMappedProvider<ArchiveId, Archive> implements ArchiveProvider {

    XmlArchiveProvider(final Map<ArchiveId, Archive> map) {
        super(map);
    }

}
