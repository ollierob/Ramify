package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.State;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "state")
class XmlState extends XmlPlace {

    @Override
    PlaceId placeId(final String id) {
        return new Spid(State.class, id);
    }

    @Override
    State place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new State(this.placeId(), this.name(), parent);
    }

    @Override
    Collection<XmlPlace> children() {
        throw new UnsupportedOperationException(); //TODO
    }

}
