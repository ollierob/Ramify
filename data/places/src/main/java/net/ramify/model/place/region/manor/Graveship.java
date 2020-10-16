package net.ramify.model.place.region.manor;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;

import javax.annotation.Nonnull;
import java.util.Set;

public class Graveship extends AbstractRegion {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Graveship");

    public Graveship(
            final PlaceId id,
            final String name,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, groupId, history);
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return Manor.CHILD_TYPES;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
