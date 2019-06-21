package net.ramify.model.place.type;

import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public interface Building extends Place {

    @Nonnull
    SettlementOrRegion parent();

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
