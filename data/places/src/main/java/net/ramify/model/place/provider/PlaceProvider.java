package net.ramify.model.place.provider;

import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.util.provider.MissingValueException;
import net.ramify.model.util.provider.Provider;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Stream;

public interface PlaceProvider extends Provider<PlaceId, Place> {

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
    Set<? extends Place> countries();

    @Nonnull
    Set<Place> findByGroup(PlaceGroupId groupId);

    @Nonnull
    Stream<Place> findByName(String name);

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
