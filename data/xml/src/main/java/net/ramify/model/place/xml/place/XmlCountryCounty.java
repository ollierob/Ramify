package net.ramify.model.place.xml.place;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.CountryCounty;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "county")
class XmlCountryCounty extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlParish.class)
    })
    private List<XmlPlace> children;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(CountryCounty.class, id);
    }

    @Override
    CountryCounty place(final Place parent) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new CountryCounty(this.placeId(), this.name(), parent);
    }

    @Override
    Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
