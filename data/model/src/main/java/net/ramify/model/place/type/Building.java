package net.ramify.model.place.type;

import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public interface Building extends Place {

    @Nonnull
    SettlementOrRegion parent();

    @Nonnull
    @Override
    default Set<Class<? extends Place>> childTypes() {
        return Collections.emptySet();
    }

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
