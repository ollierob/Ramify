package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.id.Spid;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "building")
class XmlChurch extends XmlPlace {

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Church.class, id);
    }

    @Override
    Church place(Place parent) {
        return new Church(this.placeId(), this.name(), parent);
    }

    @Override
    Collection<XmlPlace> children() {
        return Collections.emptyList();
    }

}
