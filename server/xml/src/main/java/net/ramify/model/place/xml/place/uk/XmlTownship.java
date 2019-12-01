package net.ramify.model.place.xml.place.uk;

import com.google.common.base.MoreObjects;
import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Township;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.building.XmlChurch;
import net.ramify.model.place.xml.place.building.XmlFarmstead;
import net.ramify.model.place.xml.place.building.XmlInn;
import net.ramify.model.place.xml.place.building.XmlMill;
import net.ramify.model.place.xml.place.building.XmlSchool;
import net.ramify.model.place.xml.place.settlement.XmlHamlet;
import net.ramify.model.place.xml.place.settlement.XmlVillage;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "township")
class XmlTownship extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlVillage.class),
            @XmlElementRef(type = XmlHamlet.class),
            @XmlElementRef(type = XmlChurch.class),
            @XmlElementRef(type = XmlSchool.class),
            @XmlElementRef(type = XmlFarmstead.class),
            @XmlElementRef(type = XmlMill.class),
            @XmlElementRef(type = XmlInn.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(Township.class, id);
    }

    @Override
    protected Township place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new Township(this.placeId(), this.name(), parent, groupId, history);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}