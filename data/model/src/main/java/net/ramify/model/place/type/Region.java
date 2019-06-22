package net.ramify.model.place.type;

import javax.annotation.Nonnull;

public interface Region extends SettlementOrRegion {

    @Deprecated
    @Override
    default Region region() {
        return this;
    }

    @Override
    Region parent();

    @Nonnull
    default Region ultimateParent() {
        Region region = this;
        while (region.parent() != region) {
            region = region.parent();
        }
        return region;
    }

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
