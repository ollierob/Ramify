package net.ramify.model.place.settlement;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;

public class Village extends AbstractSettlement {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Village");

    public Village(
            final PlaceId id,
            final String name,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, groupId, history);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
