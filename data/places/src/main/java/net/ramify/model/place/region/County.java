package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountrySubdivisionIso;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.district.Borough;
import net.ramify.model.place.region.district.MetropolitanBorough;
import net.ramify.model.place.region.manor.Manor;
import net.ramify.model.place.settlement.City;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class County extends AbstractRegion implements CountyOrSubdivision {

    private static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(
            Manor.class, Parish.class, City.class, Hundred.class,
            MetropolitanBorough.class, Borough.class);

    private static final PlaceProto.PlaceType PLACE_TYPE = placeTypeBuilder("County").setCanPrefix(true).setCanSuffix(false).build();

    private final CountrySubdivisionIso iso;

    public County(
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
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

    @Nonnull
    @Override
    public Optional<CountrySubdivisionIso> iso() {
        return Optional.ofNullable(iso);
    }

}
