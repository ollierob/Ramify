package net.ramify.model.place.position;

import net.ramify.model.util.Provider;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;

public interface PositionProvider extends Provider<PlaceId, Position> {

    @Nonnull
    @Override
    default Position require(final PlaceId key) throws UnknownPositionException {
        return this.requireOrThrow(key, UnknownPositionException::new);
    }

    class UnknownPositionException extends RuntimeException {

        private final PlaceId placeId;

        public UnknownPositionException(final PlaceId placeId) {
            super("Unknown position for " + placeId);
            this.placeId = placeId;
        }
    }

}
