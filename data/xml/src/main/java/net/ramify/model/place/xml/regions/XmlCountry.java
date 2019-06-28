package net.ramify.model.place.xml.regions;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

public class XmlCountry extends XmlRegion {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlElements({
            @XmlElement(name = "county", type = XmlCountryCounty.class),
            @XmlElement(name = "state", type = XmlState.class)
    })
    private List<XmlRegion> regions;

    @Nonnull
    @Override
    public PlaceId placeId() {
        return new Spid(Country.class, id);
    }

}
