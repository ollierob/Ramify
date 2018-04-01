package net.ramify.model.place.address;

import net.ramify.model.place.Country;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
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

    @Nonnull
    default <P extends Place> Optional<P> find(final Class<P> clazz) {
        return this.placeStream(clazz).findAny();
    }

    @Nonnull
    default Optional<Country> country() {
        return this.find(Country.class);
    }

}
