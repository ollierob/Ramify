package net.ramify.model.place;

import net.ramify.model.place.provider.PlaceProvider;

import javax.annotation.Nonnull;

public interface HasPlaceId {

    @Nonnull
    PlaceId placeId();

    @Nonnull
    default <P extends Place> P resolvePlace(final PlaceProvider<P> lookup) {
        return lookup.require(this.placeId());
    }

}
