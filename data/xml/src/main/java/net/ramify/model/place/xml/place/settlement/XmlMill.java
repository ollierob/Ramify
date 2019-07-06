package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.building.Mill;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "mill")
public class XmlMill extends XmlBuilding<Mill> {

    XmlMill() {
        super(Mill.class);
    }

    @Override
    protected Mill place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Mill(this.placeId(), this.name(), parent);
    }

}
