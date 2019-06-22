package net.ramify.model.place.type;

import javax.annotation.Nonnull;

public interface Settlement extends SettlementOrRegion {

    @Nonnull
    default Region parent() {
        return this.region();
    }

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
