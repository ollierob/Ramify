package net.ramify.model.place.xml.regions;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

public class XmlCountry extends XmlRegion {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElements({
            @XmlElement(name = "county", type = XmlCountryCounty.class),
            @XmlElement(name = "state", type = XmlState.class)
    })
    private List<XmlRegion> regions;

    @Nonnull
    PlaceId placeId(final String id) {
        return new Spid(Country.class, id);
    }

    @Override
    Place place(final PlaceId id, final Place parent) {
        return new Country(id, name);
    }

}
