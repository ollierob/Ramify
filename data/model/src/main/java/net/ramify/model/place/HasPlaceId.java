package net.ramify.model.place;

import net.ramify.model.place.provider.PlaceProvider;

import javax.annotation.Nonnull;

public interface HasPlaceId {

    @Nonnull
    PlaceId placeId();

    @Nonnull
    default Place resolvePlace(final PlaceProvider lookup) {
        return lookup.require(this.placeId());
    }

}
