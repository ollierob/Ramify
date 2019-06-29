package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Township;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "township")
class XmlTownship extends XmlPlace {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElements({
            @XmlElement(name = "building", type = XmlBuilding.class),
    })
    private List<XmlPlace> children;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Township.class, id);
    }

    @Override
    Township place(final PlaceId id, final Place parent) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    void addPlaces(final Set<PlaceId> places) {
        children.forEach(child -> child.addPlaces(places));
    }

}
