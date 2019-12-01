package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;
import java.util.Set;

public class Township extends AbstractRegion {

    private final Region parent;

    public Township(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId, final PlaceHistory history) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class), groupId, history);
    }

    public Township(final PlaceId id, final String name, final Region parent, final PlaceGroupId groupId, final PlaceHistory history) {
        super(id, name, groupId, history);
        this.parent = parent;
    }

    @Override
    public Region parent() {
        return parent;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return ImmutableSet.of();
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.TOWNSHIP;
    }

}
