package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.School;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "school")
public class XmlSchool extends XmlPlace {

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(School.class, id);
    }

    @Override
    protected School place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new School(this.placeId(), this.name(), parent);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return Collections.emptyList();
    }

}
