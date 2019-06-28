package net.ramify.model.place.xml.regions;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Township;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Set;

public class XmlTownship extends XmlPlace {

    @XmlAttribute(name = "name")
    private String name;

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
        throw new UnsupportedOperationException();
    }

}
