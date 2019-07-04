package net.ramify.model.place.xml.place.settlement;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.settlement.Town;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "town")
public class XmlTown extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlChurch.class),
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(Town.class, id);
    }

    @Override
    protected Town place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Town(this.placeId(), this.name(), parent);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
