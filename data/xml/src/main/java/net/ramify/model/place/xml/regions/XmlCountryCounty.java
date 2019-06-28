package net.ramify.model.place.xml.regions;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.CountryCounty;

import javax.xml.bind.annotation.XmlAttribute;

public class XmlCountryCounty extends XmlRegion {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(CountryCounty.class, id);
    }

    @Override
    Place place(final PlaceId id, final Place parent) {
        return new CountryCounty(id, name, parent.requireAs(Country.class));
    }

}
