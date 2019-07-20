package net.ramify.model.place.xml.description;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "placeId")
public class XmlPlaceId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    public PlaceId placeId() {
        return new PlaceId(id);
    }

}
