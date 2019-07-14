package net.ramify.model.place.xml.place.manor;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.manor.Graveship;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "graveship")
class XmlGraveship extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlManor.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(Graveship.class, id);
    }

    @Override
    protected Graveship place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Graveship(this.placeId(), this.name(), parent);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
