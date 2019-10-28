package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.building.Inn;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "inn")
public class XmlInn extends XmlBuilding<Inn> {

    XmlInn() {
        super(Inn.class);
    }

    @Override
    protected Inn place(final Place parent, final PlaceGroupId groupId) throws Place.InvalidPlaceTypeException {
        return new Inn(this.placeId(), this.name(), parent, groupId);
    }

}
