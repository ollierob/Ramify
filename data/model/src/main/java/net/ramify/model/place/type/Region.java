package net.ramify.model.place.type;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Region extends SettlementOrRegion {

    @Nonnull
    Optional<Region> parent();

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
