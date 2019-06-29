package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.settlement.Town;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "town")
class XmlTown extends XmlPlace {

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Town.class, id);
    }

    @Override
    Town place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Town(this.placeId(), this.name(), parent);
    }

    @Override
    Collection<XmlPlace> children() {
        return Collections.emptyList();
    }

}
