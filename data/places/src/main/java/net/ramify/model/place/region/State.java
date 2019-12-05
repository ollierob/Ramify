package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.iso.CountrySubdivisionIso;
import net.ramify.model.place.settlement.City;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class State extends AbstractRegion {

    static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(County.class, City.class);

    private final Country country;
    private final CountrySubdivisionIso iso;

    public State(final PlaceId id, final String name, final Place parent, final CountrySubdivisionIso iso, final PlaceGroupId groupId) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Country.class), iso, groupId);
    }

    public State(final PlaceId id, final String name, final Country country, final CountrySubdivisionIso iso, final PlaceGroupId groupId) {
        super(id, name, groupId);
        this.country = country;
        this.iso = iso;
    }

    @Nonnull
    @Override
    public Country parent() {
        return country;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return CHILD_TYPES;
    }

    @Nonnull
    @Override
    public Optional<CountrySubdivisionIso> iso() {
        return Optional.ofNullable(iso);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.STATE;
    }

}
