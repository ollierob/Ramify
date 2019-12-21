package net.ramify.model.place.region.district;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;

public class CityDistrict extends AbstractRegion implements District {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("District");

    private final Region parent;

    public CityDistrict(
            final PlaceId id,
            final String name,
            final Region parent,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, groupId, history);
        this.parent = parent;
    }

    @Override
    public Region parent() {
        return parent;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
