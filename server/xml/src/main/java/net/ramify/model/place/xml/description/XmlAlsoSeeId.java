package net.ramify.model.place.xml.description;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "alsoSee")
class XmlAlsoSeeId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    PlaceId placeId() {
        return new PlaceId(id);
    }

}
