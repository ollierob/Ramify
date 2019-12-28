package net.ramify.model.place.xml.place.usa;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.region.XmlRegion;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "country")
class XmlCountry extends XmlRegion<Country> {

    @XmlAttribute(name = "iso", required = true)
    private String iso;

    @XmlElementRef
    private List<XmlState> states;

    XmlCountry() {
        super(Country.class);
    }

    @Override
    protected Country toPlace(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Country(this.placeId(context), this.name(), this.iso(), Country.cast(parent), groupId, history);
    }

    protected CountryIso iso() {
        return CountryIso.valueOf(iso);
    }

    @Override
    protected Collection<XmlState> children() {
        return Objects.requireNonNull(states, "Country has no children");
    }

}
