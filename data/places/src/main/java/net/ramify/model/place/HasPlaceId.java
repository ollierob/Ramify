package net.ramify.model.place;

import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.util.provider.Provider;

import javax.annotation.Nonnull;

public interface HasPlaceId {

    @Nonnull
    PlaceId placeId();

    @Nonnull
    default CountryIso countryIso() {
        return this.placeId().countryIso();
    }

    @Nonnull
    default <P> P resolvePlace(final Provider<PlaceId, P> lookup) {
        return lookup.require(this.placeId());
    }

    @Nonnull
    default PlaceGroupId placeGroupId() {
        return this.placeId().placeGroupId();
    }

}
