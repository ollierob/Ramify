package net.ramify.model.place.xml.place.building;

import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.building.Inn;
import net.ramify.model.place.type.BuildingHistory;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "inn")
public class XmlInn extends XmlBuilding<Inn> {

    XmlInn() {
        super(Inn.class);
    }

    @Override
    protected Inn place(final Place parent, final PlaceGroupId groupId, final BuildingHistory history, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new Inn(this.placeId(), this.name(), parent, groupId, history);
    }

}
