package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.building.Graveyard;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "graveyard")
public class XmlGraveyard extends XmlBuilding<Graveyard> {

    XmlGraveyard() {
        super(Graveyard.class);
    }

    @Override
    protected Graveyard place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Graveyard(this.placeId(), this.name(), parent);
    }

}
