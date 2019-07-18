package net.ramify.model.place.xml.archives;

import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.record.archive.Archive;
import net.ramify.utils.collections.ListUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "archives")
@XmlRootElement(name = "archives")
public class XmlArchives {

    @XmlElementRef
    private List<XmlArchive> archives;

    @Nonnull
    public Collection<Archive> buildArchives() {
        if (archives == null) return Collections.emptyList();
        return ListUtils.eagerlyTransform(archives, XmlArchive::build);
    }

}
