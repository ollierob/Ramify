package net.ramify.model.place.xml.place.usa;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.County;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.settlement.XmlCity;
import net.ramify.model.place.xml.place.settlement.XmlTown;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "county")
class XmlCounty extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlTownship.class),
            @XmlElementRef(type = XmlCity.class),
            @XmlElementRef(type = XmlTown.class)
    })
    private List<XmlPlace> children;

    @Override
    protected Spid placeId(final String id, final CountryIso iso) {
        return new Spid(iso, County.class, id);
    }

    @Override
    protected Place place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new County(this.placeId(context), this.name(), parent, null, groupId, null);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
