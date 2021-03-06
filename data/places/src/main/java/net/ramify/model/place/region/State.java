package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountrySubdivisionIso;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.settlement.City;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class State extends AbstractRegion {

    static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(County.class, City.class);
    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("State");

    private final CountrySubdivisionIso iso;

    public State(
            final PlaceId id,
            final String name,
            final CountrySubdivisionIso iso,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, groupId, history);
        this.iso = iso;
    }

    @Nonnull
    @Override
    public Optional<CountrySubdivisionIso> iso() {
        return Optional.ofNullable(iso);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
