package net.ramify.model.place.region.district;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.region.County;

public class RuralDistrict extends AbstractRegion implements District {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Rural District");

    private final County parent;

    public RuralDistrict(
            final PlaceId id,
            final String name,
            final County parent,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, groupId, history);
        this.parent = parent;
    }

    @Override
    public County parent() {
        return parent;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
