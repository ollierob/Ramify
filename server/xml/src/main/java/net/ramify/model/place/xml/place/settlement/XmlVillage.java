package net.ramify.model.place.xml.place.settlement;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.settlement.Village;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.building.XmlChurch;
import net.ramify.model.place.xml.place.building.XmlFarmstead;
import net.ramify.model.place.xml.place.building.XmlGraveyard;
import net.ramify.model.place.xml.place.building.XmlInn;
import net.ramify.model.place.xml.place.building.XmlMill;
import net.ramify.model.place.xml.place.building.XmlSchool;
import net.ramify.model.place.xml.place.building.XmlStreet;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "village")
public class XmlVillage extends XmlSettlement {

    @XmlElementRefs({
            @XmlElementRef(type = XmlStreet.class),
            @XmlElementRef(type = XmlChurch.class),
            @XmlElementRef(type = XmlMill.class),
            @XmlElementRef(type = XmlSchool.class),
            @XmlElementRef(type = XmlInn.class),
            @XmlElementRef(type = XmlGraveyard.class),
            @XmlElementRef(type = XmlFarmstead.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id, final CountryIso countryIso) {
        return new Spid(countryIso, Village.class, id);
    }

    @Override
    protected Village place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Village(this.placeId(context), this.name(), parent, groupId, history);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
