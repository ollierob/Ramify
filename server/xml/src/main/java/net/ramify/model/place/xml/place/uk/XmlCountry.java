package net.ramify.model.place.xml.place.uk;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "country")
class XmlCountry extends XmlPlace {

    @XmlAttribute(name = "iso")
    private String iso;

    @XmlElementRefs({
            @XmlElementRef(type = XmlCountry.class),
            @XmlElementRef(type = XmlCounty.class)
    })
    private List<XmlPlace> children;

    @Nonnull
    protected PlaceId placeId(final String id, final CountryIso iso) {
        return new PlaceId(this.iso == null ? iso : CountryIso.valueOf(this.iso), Country.class, id);
    }

    @Override
    protected Place toPlace(final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Country(this.placeId(context), this.name(), this.iso(), groupId, history);
    }

    protected CountryIso iso() {
        return CountryIso.valueOf(iso);
    }

    @Override
    protected Collection<? extends XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
