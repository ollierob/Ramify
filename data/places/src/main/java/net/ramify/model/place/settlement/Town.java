package net.ramify.model.place.settlement;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

public class Town extends AbstractSettlement {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Town");

    public Town(
            final PlaceId id,
            final String name,
            final Region region,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, region, groupId, history);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
