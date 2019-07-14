package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.building.Farmstead;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "farmstead")
public class XmlFarmstead extends XmlBuilding<Farmstead> {

    XmlFarmstead() {
        super(Farmstead.class);
    }

    @Override
    protected Farmstead place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Farmstead(this.placeId(), this.name(), parent);
    }

}