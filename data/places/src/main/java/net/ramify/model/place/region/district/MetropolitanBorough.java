package net.ramify.model.place.region.district;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountrySubdivisionIso;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.type.Settlement;

import javax.annotation.Nonnull;
import java.util.Set;

public class MetropolitanBorough extends Borough {

    private static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Region.class, Settlement.class);
    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Metropolitan Borough");

    public MetropolitanBorough(
            final PlaceId id,
            final String name,
            final PlaceGroupId groupId,
            final CountrySubdivisionIso iso,
            final PlaceHistory history) {
        super(id, name, groupId, iso, history);
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
