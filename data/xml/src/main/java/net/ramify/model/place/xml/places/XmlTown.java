package net.ramify.model.place.xml.places;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.settlement.Town;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "town")
class XmlTown extends XmlPlace {

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Town.class, id);
    }

    @Override
    Place place(final PlaceId id, final Place parent) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    void addPlaces(final Set<PlaceId> places) {
        throw new UnsupportedOperationException(); //TODO
    }
}
