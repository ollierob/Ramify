package net.ramify.model.place.xml.place.usa;

import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "country")
class XmlCountry extends XmlPlace {

    @XmlAttribute(name = "iso", required = true)
    private String iso;

    @XmlElementRef
    private List<XmlState> states;

    @Nonnull
    protected PlaceId placeId(final String id) {
        return new Spid(Country.class, id);
    }

    @Override
    protected Place place(final Place parent, final PlaceGroupId groupId, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new Country(this.placeId(), this.name(), iso, parent);
    }

    @Override
    protected Collection<XmlState> children() {
        return Objects.requireNonNull(states, "Country has no children");
    }

}
