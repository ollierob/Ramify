package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.building.School;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "school")
public class XmlSchool extends XmlBuilding<School> {

    XmlSchool() {
        super(School.class);
    }

    @Override
    protected School place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new School(this.placeId(), this.name(), parent);
    }

}
