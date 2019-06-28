package net.ramify.model.place;

import javax.annotation.Nonnull;

public interface HasPlace extends HasPlaceId {

    @Nonnull
    Place place();

    @Nonnull
    @Override
    default PlaceId placeId() {
        return this.place().placeId();
    }

}
