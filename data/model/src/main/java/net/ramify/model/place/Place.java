package net.ramify.model.place;

import net.ramify.model.place.type.PlaceHandler;

import javax.annotation.Nonnull;

public interface Place extends HasPlaceId {

    @Nonnull
    String name();

    @Nonnull
    Place parent();

    default boolean contains(@Nonnull final Place that) {
        var parent = that;
        while (!this.equals(parent)) {
            final var nextParent = parent.parent();
            if (nextParent == parent) return false;
            parent = nextParent;
        }
        return true;
    }

    <R> R handleWith(PlaceHandler<R> handler);

}
