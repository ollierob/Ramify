package net.ramify.model.place.xml.archives;

import net.ramify.model.place.xml.description.XmlTextLink;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.record.archive.Archive;
import net.ramify.model.record.archive.ArchiveId;
import net.ramify.model.record.archive.HasArchiveId;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "archive")
public class XmlArchive implements HasArchiveId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "iconHref")
    private String iconHref;

    @XmlElement(namespace = XmlPlace.NAMESPACE, name = "description")
    private String description;

    @XmlElementRef
    private XmlTextLink link;

    @Nonnull
    Archive build() {
        return new Archive(
                this.archiveId(),
                name,
                description,
                link,
                iconHref);
    }

    @Nonnull
    @Override
    public ArchiveId archiveId() {
        return new ArchiveId(id);
    }
}
