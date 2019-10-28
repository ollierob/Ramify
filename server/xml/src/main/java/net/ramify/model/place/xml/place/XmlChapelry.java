package net.ramify.model.place.xml.place;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.region.Chapelry;
import net.ramify.model.place.xml.place.settlement.XmlChurch;
import net.ramify.model.place.xml.place.settlement.XmlSchool;
import net.ramify.model.place.xml.place.settlement.XmlTown;
import net.ramify.model.place.xml.place.settlement.XmlVillage;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "chapelry")
public class XmlChapelry extends XmlArea<Chapelry> {

    @XmlElementRefs({
            @XmlElementRef(type = XmlTownship.class),
            @XmlElementRef(type = XmlTown.class),
            @XmlElementRef(type = XmlVillage.class),
            @XmlElementRef(type = XmlChurch.class),
            @XmlElementRef(type = XmlSchool.class)
    })
    private List<XmlPlace> children;

    XmlChapelry() {
        super(Chapelry.class);
    }

    @Override
    protected Chapelry place(final Place parent, final PlaceGroupId groupId) throws Place.InvalidPlaceTypeException {
        return new Chapelry(this.placeId(), this.name(), parent, groupId);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
