package net.ramify.model.place.xml.place;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Parish;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "parish")
public class XmlParish extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlParish.class),
            @XmlElementRef(type = XmlTownship.class),
            @XmlElementRef(type = XmlTown.class),
            @XmlElementRef(type = XmlVillage.class)
    })
    private List<XmlPlace> children;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Parish.class, id);
    }

    @Override
    Parish place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Parish(this.placeId(), this.name(), parent);
    }

    @Override
    Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
