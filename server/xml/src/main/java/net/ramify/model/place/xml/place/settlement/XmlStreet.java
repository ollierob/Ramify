package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.building.Street;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "street")
public class XmlStreet extends XmlBuilding<Street> {

    XmlStreet() {
        super(Street.class);
    }

    @Override
    protected Street place(Place parent) throws Place.InvalidPlaceTypeException {
        return new Street(this.placeId(), this.name(), parent);
    }

}
