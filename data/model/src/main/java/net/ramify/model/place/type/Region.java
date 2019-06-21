package net.ramify.model.place.type;

import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public interface Region extends SettlementOrRegion {

    @Deprecated
    @Override
    default Region region() {
        return this;
    }

    @Nonnull
    Region parent();

    @Nonnull
    default Region ultimateParent() {
        Region region = this;
        while (region.parent() != region) {
            region = region.parent();
        }
        return region;
    }

    default boolean contains(@Nonnull final Region region) {
        return this.equals(region.ultimateParent());
    }

    @Override
    default boolean contains(@Nonnull final Place place) {
        return place instanceof HasRegion
                && this.contains(((HasRegion) place).region());
    }

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
