package net.ramify.model.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class Country extends AbstractRegion {

    private final Country parent;
    private final String iso;

    public Country(final PlaceId id, final String name, final String iso) {
        this(id, name, iso, null);
    }

    public Country(final PlaceId id, final String name, final String iso, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, iso, Country.cast(parent));
    }

    public Country(final PlaceId id, final String name, final String iso, final Country parent) {
        super(id, name);
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
        return Collections.singleton(County.class);
    }

    @Nonnull
    @Override
    public Optional<String> iso() {
        return Optional.of(iso);
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.COUNTRY;
    }

    @CheckForNull
    public static Country cast(final Place place) throws InvalidPlaceTypeException {
        return place == null ? null : place.requireAs(Country.class);
    }

}
