package net.ramify.model.place.xml.place;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Township;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "township")
class XmlTownship extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlChurch.class),
            @XmlElementRef(type = XmlVillage.class)
    })
    private List<XmlPlace> children;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Township.class, id);
    }

    @Override
    Township place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Township(this.placeId(), this.name(), parent);
    }

    @Override
    Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
