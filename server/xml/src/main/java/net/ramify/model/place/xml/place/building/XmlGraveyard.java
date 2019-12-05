package net.ramify.model.place.xml.place.building;

import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.building.Graveyard;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "graveyard")
public class XmlGraveyard extends XmlBuilding<Graveyard> {

    XmlGraveyard() {
        super(Graveyard.class);
    }

    @Override
    protected Graveyard place(final Place parent, final PlaceGroupId groupId, final BuildingHistory history, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new Graveyard(this.placeId(parent), this.name(), parent, groupId, history);
    }

}
