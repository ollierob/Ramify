package net.ramify.model.place.building;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Building;

public class Inn extends AbstractBuilding implements Building {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Inn");

    public Inn(
            final PlaceId id,
            final String name,
            final PlaceGroupId groupId,
            final BuildingHistory history) {
        super(id, name, groupId, history);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
