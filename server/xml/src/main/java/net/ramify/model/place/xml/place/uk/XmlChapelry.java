package net.ramify.model.place.xml.place.uk;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.Chapelry;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.building.XmlChurch;
import net.ramify.model.place.xml.place.building.XmlSchool;
import net.ramify.model.place.xml.place.region.XmlRegion;
import net.ramify.model.place.xml.place.settlement.XmlTown;
import net.ramify.model.place.xml.place.settlement.XmlVillage;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "chapelry")
public class XmlChapelry extends XmlRegion<Chapelry> {

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
    protected Chapelry toPlace(final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Chapelry(this.placeId(context), this.name(), groupId, history);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
