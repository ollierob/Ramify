package net.ramify.model.place.xml.place.group;

import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.group.DefaultPlaceGroup;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "placeGroup")
public class XmlPlaceGroup {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "defaultChildType", required = true)
    private String defaultChildType;

    PlaceGroup toPlaceGroup(final PlaceProvider placeProvider) {
        final var id = this.placeGroupId();
        return new DefaultPlaceGroup(
                id,
                name,
                id.withPlaceType(defaultChildType),
                placeProvider.findByGroup(id));
    }

    protected PlaceGroupId placeGroupId() {
        return new PlaceGroupId(id);
    }

}
