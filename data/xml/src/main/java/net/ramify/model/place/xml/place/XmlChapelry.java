package net.ramify.model.place.xml.place;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Chapelry;
import net.ramify.model.place.xml.place.settlement.XmlTown;
import net.ramify.model.place.xml.place.settlement.XmlVillage;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "chapelry")
public class XmlChapelry extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlTownship.class),
            @XmlElementRef(type = XmlTown.class),
            @XmlElementRef(type = XmlVillage.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(Chapelry.class, id);
    }

    @Override
    protected Chapelry place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Chapelry(this.placeId(), this.name(), parent);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
