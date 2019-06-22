package net.ramify.model.place;

import net.ramify.model.Castable;
import net.ramify.model.place.type.PlaceHandler;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Place extends HasPlaceId, Castable<Place> {

    @Nonnull
    String name();

    @Nonnull
    Place parent();

    default boolean contains(@Nonnull final Place that) {
        if (this.equals(that)) return true;
        final var parent = that.parent();
        if (parent.equals(that)) return false;
        return this.contains(parent);
    }

    @Nonnull
    default <P extends Place> Optional<P> find(final Class<P> type) {
        if (this.is(type)) return this.as(type);
        final var parent = this.parent();
        if (this.equals(parent)) return Optional.empty();
        return parent.find(type);
    }

    <R> R handleWith(PlaceHandler<R> handler);

}
