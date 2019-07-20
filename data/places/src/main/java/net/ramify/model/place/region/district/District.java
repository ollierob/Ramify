package net.ramify.model.place.region.district;

import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

public interface District extends Region {

    @Override
    default PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.DISTRICT;
    }

}
