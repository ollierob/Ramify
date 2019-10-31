package net.ramify.model.place.region.district;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;
import java.util.Set;

public class MetropolitanBorough extends AbstractRegion implements District {

    private final Region parent;

    public MetropolitanBorough(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class), groupId);
    }

    public MetropolitanBorough(final PlaceId id, final String name, final Region parent, final PlaceGroupId groupId) {
        super(id, name, groupId);
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
        return PlaceProto.PlaceType.BOROUGH;
    }

}
