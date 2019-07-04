package net.ramify.model.place.xml.description;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "placeDescription")
class XmlPlaceDescription {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlValue
    private String description;

    @Nonnull
    PlaceId placeId() {
        return new PlaceId(id);
    }

    @Nonnull
    String description() {
        return description.trim();
    }

}
