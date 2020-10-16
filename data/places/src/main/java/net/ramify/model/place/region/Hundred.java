package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.manor.Manor;

import javax.annotation.Nonnull;
import java.util.Set;

public class Hundred extends AbstractRegion implements CountyOrSubdivision {

    static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Hundred.class, Parish.class, Manor.class);

    private final PlaceProto.PlaceType type;

    public Hundred(
            final PlaceId id,
            final String name,
            final PlaceGroupId groupId,
            final PlaceHistory history,
            final PlaceProto.PlaceType type) {
        super(id, name, groupId, history);
        this.type = type;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return CHILD_TYPES;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        return type;
    }

}
