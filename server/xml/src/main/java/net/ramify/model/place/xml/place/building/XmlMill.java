package net.ramify.model.place.xml.place.building;

import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.building.Mill;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "mill")
public class XmlMill extends XmlBuilding<Mill> {

    XmlMill() {
        super(Mill.class);
    }

    @Override
    protected Mill place(final Place parent, final PlaceGroupId groupId, final BuildingHistory history, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new Mill(this.placeId(), this.name(), parent, groupId, history);
    }

}
