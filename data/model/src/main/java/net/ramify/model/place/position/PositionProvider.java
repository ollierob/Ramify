package net.ramify.model.place.position;

import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.util.MissingValueException;
import net.ramify.model.util.Provider;

import javax.annotation.Nonnull;

public interface PositionProvider extends Provider<PlaceId, Position> {

    @Nonnull
    @Override
    default Position require(final PlaceId key) throws UnknownPositionException {
        return this.requireOrThrow(key, UnknownPositionException::new);
    }

    class UnknownPositionException extends MissingValueException implements HasPlaceId {

        private final PlaceId placeId;

        public UnknownPositionException(final PlaceId placeId) {
            super("Unknown position for " + placeId);
            this.placeId = placeId;
        }

        @Nonnull
        @Override
        public PlaceId placeId() {
            return placeId;
        }

    }

}
