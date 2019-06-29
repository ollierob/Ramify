package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.settlement.Village;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "village")
class XmlVillage extends XmlPlace {

    @XmlElements({
            @XmlElement(name = "church", type = XmlChurch.class),
    })
    private List<XmlPlace> children;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Village.class, id);
    }

    @Override
    Village place(final PlaceId id, final String name, final Place parent) {
        return new Village(id, name, parent);
    }

    @Override
    void addPlaces(Set<PlaceId> places) {
        places.add(this.placeId());
        children.forEach(child -> child.addPlaces(places));
    }

}
