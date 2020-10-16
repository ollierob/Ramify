package net.ramify.model.place.xml.place.building;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.building.Inn;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "inn")
public class XmlInn extends XmlBuilding<Inn> {

    XmlInn() {
        super(Inn.class);
    }

    @Override
    protected Inn toPlace(final PlaceGroupId groupId, final BuildingHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Inn(this.placeId(context), this.name(), groupId, history);
    }

}
