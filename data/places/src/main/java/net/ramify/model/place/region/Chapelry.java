package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Institution;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.settlement.Town;
import net.ramify.model.place.settlement.Village;

import javax.annotation.Nonnull;
import java.util.Set;

public class Chapelry extends AbstractRegion {

    private static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Township.class, Town.class, Village.class, Institution.class);

    private final Parish parent;

    public Chapelry(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Parish.class));
    }

    public Chapelry(final PlaceId id, final String name, final Parish parent) {
        super(id, name);
        this.parent = parent;
    }

    @Nonnull
    @Override
    public Parish parent() {
        return parent;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return CHILD_TYPES;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.CHAPELRY;
    }

}
