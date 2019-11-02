package net.ramify.model.place.provider;

import net.ramify.model.Provider;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

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
    default Optional<Place> parent(final PlaceId id) {
        return this.maybeGet(id).map(Place::parent);
    }

    @Nonnull
    default Set<Place> children(final PlaceId id, final int depth) {
        return this.children(id, depth, place -> true);
    }

    @Nonnull
    Set<Place> children(PlaceId id, int depth, Predicate<Place> placePredicate);

    @Nonnull
    Set<? extends Place> countries();

    @Nonnull
    Set<Place> findByName(String name, int limit);

    class UnknownPlaceException extends RuntimeException {

        private final PlaceId placeId;

        public UnknownPlaceException(final PlaceId placeId) {
            super("Unknown place: " + placeId);
            this.placeId = placeId;
        }

    }

}
