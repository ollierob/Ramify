package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.settlement.City;
import net.ramify.model.place.settlement.Town;
import net.ramify.model.place.settlement.Village;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;
import java.util.Set;

public class Parish extends AbstractRegion {

    static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Chapelry.class, Township.class, City.class, Town.class, Village.class, Church.class);

    private final Region parent;

    public Parish(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class), groupId);
    }

    public Parish(final PlaceId id, final String name, final Region parent, final PlaceGroupId groupId) {
        super(id, name, groupId);
        this.parent = parent;
    }

    @Nonnull
    @Override
    public Region parent() {
        return parent;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return CHILD_TYPES;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.PARISH;
    }

}
