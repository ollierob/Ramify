package net.ramify.model.place.region.district;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.iso.CountrySubdivisionIso;
import net.ramify.model.place.type.Region;

public class MunicipalBorough extends Borough {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Municipal Borough");

    public MunicipalBorough(
            final PlaceId id,
            final String name,
            final Region parent,
            final PlaceGroupId groupId,
            final CountrySubdivisionIso iso,
            final PlaceHistory history) {
        super(id, name, parent, groupId, iso, history);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
