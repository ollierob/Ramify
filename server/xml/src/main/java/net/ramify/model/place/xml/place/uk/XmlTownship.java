package net.ramify.model.place.xml.place.uk;

import com.google.common.base.MoreObjects;
import net.ramify.model.ParserContext;
import net.ramify.model.date.InYears;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Township;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.building.XmlBuilding;
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

    static final PlaceHistory DEFAULT_HISTORY = new DefaultPlaceHistory(null, new InYears(1866));

    @XmlElementRefs({
            @XmlElementRef(type = XmlHamlet.class),
            @XmlElementRef(type = XmlVillage.class),
            @XmlElementRef(type = XmlBuilding.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id, final CountryIso iso) {
        return new Spid(iso, Township.class, id);
    }

    @Override
    protected Township place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Township(this.placeId(context), this.name(), parent, groupId, history);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

    @Override
    protected PlaceHistory history(final ParserContext context) {
        return MoreObjects.firstNonNull(super.history(context), DEFAULT_HISTORY);
    }

}
