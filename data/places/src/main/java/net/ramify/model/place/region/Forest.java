package net.ramify.model.place.region;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;

public class Forest extends AbstractRegion {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeTypeBuilder("Forest").setCanPrefix(true).setCanSuffix(true).build();

    private final CountyOrSubdivision parent;

    public Forest(
            final PlaceId id,
            final String name,
            final CountyOrSubdivision parent,
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
