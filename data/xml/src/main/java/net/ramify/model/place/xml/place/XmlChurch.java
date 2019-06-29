package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.type.SettlementOrRegion;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "building")
class XmlChurch extends XmlPlace {

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Church.class, id);
    }

    @Override
    Church place(final PlaceId id, final String name, final Place parent) {
        return new Church(id, name, parent.requireAs(SettlementOrRegion.class));
    }

    @Override
    void addPlaces(final Set<PlaceId> places) {
        places.add(this.placeId());
    }

}
