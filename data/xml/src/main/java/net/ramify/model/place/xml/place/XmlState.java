package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.State;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "state")
class XmlState extends XmlPlace {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(State.class, id);
    }

    @Override
    State place(PlaceId id, Place parent) {
        return new State(id, name, parent.requireAs(Country.class));
    }

    @Override
    void addPlaces(final Set<PlaceId> places) {
        //this.places.forEach(region -> region.addPlaces(places));
    }

}
