package net.ramify.model.place.provider;

import net.ramify.model.Provider;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;

public interface PlaceProvider extends Provider<PlaceId, Place> {

    @Nonnull
    default <P extends Place> P require(final PlaceId id, final Class<P> type) {
        return this.require(id).requireAs(type);
    }

}
