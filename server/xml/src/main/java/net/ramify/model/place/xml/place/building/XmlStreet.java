package net.ramify.model.place.xml.place.building;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.building.Street;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.type.SettlementOrRegion;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "street")
public class XmlStreet extends XmlBuilding<Street> {

    XmlStreet() {
        super(Street.class);
    }

    @Override
    protected Street place(final Place parent, final PlaceGroupId groupId, final BuildingHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new Street(this.placeId(context), this.name(), parent.requireAs(SettlementOrRegion.class), groupId, history);
    }

}
