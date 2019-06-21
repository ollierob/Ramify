package net.ramify.model.place.type;

import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public interface Settlement extends SettlementOrRegion {

    @Nonnull
    default Country country() {
        return this.region().country();
    }

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

    @Override
    default boolean contains(@Nonnull Place that) {
        throw new UnsupportedOperationException();
    }

}
