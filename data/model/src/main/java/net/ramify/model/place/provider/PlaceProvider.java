package net.ramify.model.place.provider;

import net.ramify.model.Provider;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;

public interface PlaceProvider extends Provider<PlaceId, Place> {

    @Nonnull
    @Override
    default Place require(final PlaceId id) {
        final var place = this.get(id);
        if (place == null) throw new UnknownPlaceException(id);
        return place;
    }

    @Nonnull
    default <P extends Place> P require(final PlaceId id, final Class<P> type) {
        return this.require(id).requireAs(type);
    }

    class UnknownPlaceException extends RuntimeException {

        private final PlaceId placeId;

        public UnknownPlaceException(final PlaceId placeId) {
            super("Unknown place: " + placeId);
            this.placeId = placeId;
        }

    }

}
