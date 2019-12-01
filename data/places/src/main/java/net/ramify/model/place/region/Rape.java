package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.Nonnull;
import java.util.Set;

public class Rape extends Hundred implements CountyOrSubdivision {

    static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Hundred.class);

    public Rape(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId, final PlaceHistory history) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(County.class), groupId, history);
    }

    public Rape(final PlaceId id, final String name, final County parent, final PlaceGroupId groupId, final PlaceHistory history) {
        super(id, name, parent, groupId, history);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.RAPE;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return CHILD_TYPES;
    }

}
