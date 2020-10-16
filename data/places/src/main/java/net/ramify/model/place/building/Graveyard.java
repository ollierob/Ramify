package net.ramify.model.place.building;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.proto.PlaceProto;

public class Graveyard extends AbstractBuilding implements Institution {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Graveyard");

    public Graveyard(
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
