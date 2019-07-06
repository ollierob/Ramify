package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "church")
public class XmlChurch extends XmlBuilding<Church> {

    XmlChurch() {
        super(Church.class);
    }

    @Override
    protected Church place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Church(this.placeId(), this.name(), parent);
    }

}
