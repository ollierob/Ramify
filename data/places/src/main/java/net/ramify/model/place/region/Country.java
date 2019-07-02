package net.ramify.model.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class Country extends AbstractRegion {

    private final Country parent;

    public Country(final PlaceId id, final String name) {
        this(id, name, null);
    }

    public Country(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, Country.cast(parent));
    }

    public Country(final PlaceId id, final String name, final Country parent) {
        super(id, name);
        this.parent = parent;
    }

    @Override
    @CheckForNull
    public Country parent() {
        return parent;
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
