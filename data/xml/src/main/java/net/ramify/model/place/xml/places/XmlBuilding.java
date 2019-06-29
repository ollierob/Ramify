package net.ramify.model.place.xml.places;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.type.Building;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "building")
class XmlBuilding extends XmlPlace {

    @XmlAttribute(name = "name")
    private String name;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Building.class, id);
    }

    @Override
    Place place(final PlaceId id, final Place parent) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    void addPlaces(final Set<PlaceId> places) {
        places.add(this.placeId());
    }

}
