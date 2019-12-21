package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class Country extends AbstractRegion {

    private static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Country.class, State.class, County.class);
    private static final PlaceProto.PlaceType PLACE_TYPE = placeTypeBuilder("Country").setCanPrefix(false).setCanSuffix(false).setIsCountry(true).build();

    @CheckForNull
    private final Country parent;
    private final CountryIso iso;

    public Country(final PlaceId id, final String name, @Nonnull final CountryIso iso, final Country parent, final PlaceGroupId groupId, final PlaceHistory history) {
        super(id, name, groupId, history);
        this.parent = parent;
        this.iso = iso;
    }

    @Override
    @CheckForNull
    public Country parent() {
        return parent;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return CHILD_TYPES;
    }

    public CountryIso countryIso() {
        return iso;
    }

    @Nonnull
    @Override
    public Optional<CountryIso> iso() {
        return Optional.of(iso);
    }

    public boolean isDefunct(final Class<? extends Place> placeType) {
        return false;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

    @CheckForNull
    public static Country cast(final Place place) throws InvalidPlaceTypeException {
        return place == null ? null : place.requireAs(Country.class);
    }

}
