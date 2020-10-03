package net.ramify.model.place.provider;

import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.util.provider.MissingValueException;
import net.ramify.model.util.provider.Provider;

import javax.annotation.Nonnull;
import java.util.Set;

public interface PlaceProvider extends Provider<PlaceId, Place> {

    int MAX_DEPTH = 5;

    @Nonnull
    @Override
    default Place require(final PlaceId id) throws UnknownPlaceException {
        return this.requireOrThrow(id, UnknownPlaceException::new);
    }

    @Nonnull
    default <P extends Place> P require(final PlaceId id, final Class<P> type) throws Place.InvalidPlaceTypeException {
        return this.require(id).requireAs(type);
    }

    @Nonnull
    Set<? extends Place> countries(boolean onlyTopLevel);

    @Nonnull
    Set<Place> findByGroup(PlaceGroupId groupId);

    class UnknownPlaceException extends MissingValueException implements HasPlaceId {

        private final PlaceId placeId;

        public UnknownPlaceException(final PlaceId placeId) {
            super("Unknown place: " + placeId);
            this.placeId = placeId;
        }

        @Nonnull
        @Override
        public PlaceId placeId() {
            return placeId;
        }

    }

}
