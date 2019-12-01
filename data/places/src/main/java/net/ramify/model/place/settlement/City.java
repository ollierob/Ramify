package net.ramify.model.place.settlement;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

public class City extends AbstractSettlement {

    public City(PlaceId id, String name, Region region, final PlaceGroupId groupId, final PlaceHistory history) {
        super(id, name, region, groupId, history);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.CITY;
    }

}
