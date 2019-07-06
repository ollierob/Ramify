package net.ramify.model.place.xml.place;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Township;
import net.ramify.model.place.xml.place.settlement.XmlChurch;
import net.ramify.model.place.xml.place.settlement.XmlFarmstead;
import net.ramify.model.place.xml.place.settlement.XmlHamlet;
import net.ramify.model.place.xml.place.settlement.XmlMill;
import net.ramify.model.place.xml.place.settlement.XmlSchool;
import net.ramify.model.place.xml.place.settlement.XmlVillage;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "township")
class XmlTownship extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlVillage.class),
            @XmlElementRef(type = XmlHamlet.class),
            @XmlElementRef(type = XmlChurch.class),
            @XmlElementRef(type = XmlSchool.class),
            @XmlElementRef(type = XmlFarmstead.class),
            @XmlElementRef(type = XmlMill.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(Township.class, id);
    }

    @Override
    protected Township place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Township(this.placeId(), this.name(), parent);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
