package net.ramify.model.place.xml.regions;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.State;

import javax.xml.bind.annotation.XmlAttribute;

public class XmlState extends XmlRegion {

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

}
