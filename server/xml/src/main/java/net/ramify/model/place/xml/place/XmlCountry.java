package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "country")
class XmlCountry extends XmlPlace {

    @XmlAttribute(name = "iso", required = true)
    private String iso;

    @XmlElementRefs({
            @XmlElementRef(type = XmlCountry.class),
            @XmlElementRef(type = XmlCounty.class),
            @XmlElementRef(type = XmlState.class)
    })
    private List<XmlPlace> children;

    @Nonnull
    protected PlaceId placeId(final String id) {
        return new Spid(Country.class, id);
    }

    @Override
    protected Place place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Country(this.placeId(), this.name(), iso, parent);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return Objects.requireNonNull(children, "Country has no children");
    }

}
