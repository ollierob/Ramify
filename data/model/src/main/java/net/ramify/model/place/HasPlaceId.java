package net.ramify.model.place;

import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.provider.PlaceProvider;

import javax.annotation.Nonnull;

public interface HasPlaceId {

    @Nonnull
    PlaceId placeId();

    @Nonnull
    default CountryIso countryIso() {
        return this.placeId().countryIso();
    }

    @Nonnull
    default Place resolvePlace(final PlaceProvider lookup) {
        return lookup.require(this.placeId());
    }

    @Nonnull
    default PlaceGroupId placeGroupId() {
        return this.placeId().placeGroupId();
    }

}
