package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.CountryCounty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "county")
class XmlCountryCounty extends XmlPlace {

    @XmlElements({
            @XmlElement(name = "parish", type = XmlParish.class)
    })
    private List<XmlPlace> children;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(CountryCounty.class, id);
    }

    @Override
    CountryCounty place(final Place parent) {
        return new CountryCounty(this.placeId(), this.name(), parent.requireAs(Country.class));
    }

    @Override
    Collection<XmlPlace> children() {
        return children;
    }

}
