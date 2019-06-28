package net.ramify.model.place.xml.regions;

import net.ramify.model.place.HasPlaceId;

import javax.xml.bind.annotation.XmlAttribute;

//@XmlSeeAlso({XmlCountry.class})
public abstract class XmlRegion implements HasPlaceId {

    @XmlAttribute(name = "id", required = true)
    String id;

    XmlRegion() {
    }

}
