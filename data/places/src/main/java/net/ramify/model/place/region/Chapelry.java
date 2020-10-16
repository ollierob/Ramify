package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.settlement.Town;
import net.ramify.model.place.settlement.Village;

import javax.annotation.Nonnull;
import java.util.Set;

public class Chapelry extends AbstractRegion {

    private static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Township.class, Town.class, Village.class, Church.class);
    public static final PlaceProto.PlaceType PLACE_TYPE = placeType("Chapelry");

    public Chapelry(
            final PlaceId id,
            final String name,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, groupId, history);
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return CHILD_TYPES;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
