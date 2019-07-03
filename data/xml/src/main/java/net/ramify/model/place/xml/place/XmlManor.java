package net.ramify.model.place.xml.place;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Manor;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "manor")
class XmlManor extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlManor.class)
    })
    private List<XmlPlace> children;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Manor.class, id);
    }

    @Override
    Manor place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Manor(this.placeId(), this.name(), parent);
    }

    @Override
    Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
