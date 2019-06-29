package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Township;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "township")
class XmlTownship extends XmlPlace {

    @XmlElements({
            @XmlElement(name = "church", type = XmlChurch.class),
            @XmlElement(name = "village", type = XmlVillage.class)
    })
    private List<XmlPlace> children;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Township.class, id);
    }

    @Override
    Township place(final Place parent) {
        return new Township(this.placeId(), this.name(), parent);
    }

    @Override
    Collection<XmlPlace> children() {
        return children;
    }

}
