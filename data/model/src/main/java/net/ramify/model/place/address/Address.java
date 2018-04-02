package net.ramify.model.place.address;

import net.ramify.model.place.Country;
import net.ramify.model.place.Location;
import net.ramify.model.place.Place;
import net.ramify.model.place.position.Position;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface Address {

    @Nonnull
    Set<Place> places();

    @Nonnull
    default Stream<Place> placeStream() {
        return this.places().stream();
    }

    @Nonnull
    default <E extends Place> Stream<E> placeStream(final Class<E> clazz) {
        return this.placeStream()
                .map(event -> event.cast(clazz))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    default boolean contains(@Nonnull final Place place) {
        return this.placeStream().anyMatch(p -> p.equals(place));
    }

    @Nonnull
    default <P extends Place> Optional<P> find(final Class<P> clazz) {
        return this.placeStream(clazz).findAny();
    }

    @Nonnull
    default Optional<Country> country() {
        return this.find(Country.class);
    }

    @Nonnull
    default Optional<Location> location() {
        return this.find(Location.class);
    }

    @Nonnull
    default Optional<Position> position() {
        return this.find(Position.class);
    }

    default Map<String, String> describe() {
        final Map<String, String> description = new HashMap<>();
        this.places().forEach(place -> description.put(place.getClass().getSimpleName(), place.name()));
        return description;
    }

    Address UNKNOWN = Set::of;

}
