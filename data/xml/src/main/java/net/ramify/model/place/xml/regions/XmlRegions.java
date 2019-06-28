package net.ramify.model.place.xml.regions;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(namespace = "http://ramify.net/places", name = "regions")
@XmlRootElement(name = "regions")
public class XmlRegions {

    @XmlElements({
            @XmlElement(name = "country", type = XmlCountry.class),
            @XmlElement(name = "state", type = XmlState.class),
            @XmlElement(name = "county", type = XmlCountryCounty.class),
            @XmlElement(name = "parish", type = XmlParish.class)
    })
    private List<XmlRegion> regions;

}
