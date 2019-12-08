package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.manor.Manor;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;

public class Hundred extends AbstractRegion implements CountyOrSubdivision {

    static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Hundred.class, Parish.class, Manor.class);

    private final CountyOrSubdivision parent;
    private final PlaceProto.PlaceType type;

    public Hundred(
            final PlaceId id,
            final String name,
            final Place parent,
            final PlaceGroupId groupId,
            final PlaceHistory history, PlaceProto.PlaceType type) throws InvalidPlaceTypeException {
        this(id, name, Objects.requireNonNull(parent, "parent").requireAs(CountyOrSubdivision.class), groupId, history, type);
    }

    public Hundred(
            final PlaceId id,
            final String name,
            final CountyOrSubdivision parent,
            final PlaceGroupId groupId,
            final PlaceHistory history, PlaceProto.PlaceType type) {
        super(id, name, groupId, history);
        this.parent = parent;
        this.type = type;
    }

    @Nonnull
    @Override
    public CountyOrSubdivision parent() {
        return parent;
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
