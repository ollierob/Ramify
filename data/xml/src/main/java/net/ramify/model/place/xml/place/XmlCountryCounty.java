package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.CountryCounty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "county")
class XmlCountryCounty extends XmlPlace {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElements({
            @XmlElement(name = "parish", type = XmlParish.class)
    })
    private List<XmlPlace> children;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(CountryCounty.class, id);
    }

    @Override
    CountryCounty place(final PlaceId id, final Place parent) {
        return new CountryCounty(id, name, parent.requireAs(Country.class));
    }

    @Override
    void addPlaces(final Set<PlaceId> places) {
        children.forEach(place -> place.addPlaces(places));
    }

}
