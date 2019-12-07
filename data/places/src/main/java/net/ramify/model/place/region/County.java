package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.district.Borough;
import net.ramify.model.place.region.district.MetropolitanBorough;
import net.ramify.model.place.region.iso.CountrySubdivisionIso;
import net.ramify.model.place.region.manor.Manor;
import net.ramify.model.place.settlement.City;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class County extends AbstractRegion implements CountyOrSubdivision {

    private static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(
            Manor.class, Parish.class, City.class, Hundred.class,
            MetropolitanBorough.class, Borough.class);

    private final Region parent;
    private final CountrySubdivisionIso iso;

    public County(final PlaceId id, final String name, final Place parent, final CountrySubdivisionIso iso, final PlaceGroupId groupId, final PlaceHistory history) throws InvalidPlaceTypeException {
        this(id, name, Objects.requireNonNull(parent).requireAs(Region.class), iso, groupId, history);
    }

    public County(final PlaceId id, final String name, final Region parent, final CountrySubdivisionIso iso, final PlaceGroupId groupId, final PlaceHistory history) {
        super(id, name, groupId, history);
        this.parent = parent;
        this.iso = iso;
    }

    @Override
    public Region parent() {
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
        return PlaceProto.PlaceType.COUNTY;
    }

    @Nonnull
    @Override
    public Optional<CountrySubdivisionIso> iso() {
        return Optional.ofNullable(iso);
    }

}
