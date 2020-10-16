package net.ramify.model.place.region.district;

import net.ramify.model.date.InYears;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;

public class UrbanDistrict extends AbstractRegion implements District {

    public static final PlaceHistory HISTORY = new DefaultPlaceHistory(new InYears(1894), new InYears(1974));
    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Urban District");

    public UrbanDistrict(
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
