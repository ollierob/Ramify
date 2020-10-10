package net.ramify.model.place.type;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Settlement extends SettlementOrRegion {

    @Nonnull
    @Override
    default Set<Class<? extends Place>> childTypes() {
        return ImmutableSet.of();
    }

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
