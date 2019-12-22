package net.ramify.model.place.xml.place.group;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.group.DefaultPlaceGroup;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import static net.ramify.utils.StringUtils.isBlank;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "placeGroup")
public class XmlPlaceGroup {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "defaultChildType")
    private String defaultChildType;

    @XmlAttribute(name = "defaultChildId")
    private String defaultChildId;

    PlaceGroup toPlaceGroup(final PlaceProvider placeProvider) {
        final var id = this.placeGroupId();
        return new DefaultPlaceGroup(
                id,
                name,
                this.defaultChild(placeProvider, id),
                placeProvider.findByGroup(id));
    }

    protected Place defaultChild(final PlaceProvider placeProvider, final PlaceGroupId id) {
        return placeProvider.require(isBlank(defaultChildId) ? id.withPlaceType(defaultChildType) : new PlaceId(defaultChildId));
    }

    protected PlaceGroupId placeGroupId() {
        return new PlaceGroupId(id);
    }

}
