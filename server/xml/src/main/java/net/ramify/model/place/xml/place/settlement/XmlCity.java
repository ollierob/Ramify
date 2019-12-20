package net.ramify.model.place.xml.place.settlement;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.region.iso.CountrySubdivisionIso;
import net.ramify.model.place.settlement.City;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.building.XmlBuilding;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "city")
public class XmlCity extends XmlSettlement {

    @XmlAttribute(name = "iso")
    private String iso;

    @XmlElementRefs({
            @XmlElementRef(type = XmlBuilding.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id, final CountryIso countryIso) {
        return new Spid(countryIso, City.class, id);
    }

    @Override
    protected City place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new City(this.placeId(context), this.name(), parent.requireAs(Region.class), groupId, history, this.iso());
    }

    CountrySubdivisionIso iso() {
        return CountrySubdivisionIso.valueOf(iso);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
