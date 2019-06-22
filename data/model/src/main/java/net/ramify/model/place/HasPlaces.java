package net.ramify.model.place;

import net.ramify.utils.collections.IterableUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasPlaces {

    @Nonnull
    Set<? extends Place> places();

    default boolean hasPlace(final PlaceId placeId) {
        return IterableUtils.any(this.places(), p -> placeId.equals(p.placeId()));
    }

    default <P extends Place> boolean hasPlace(final Class<P> type) {
        return IterableUtils.has(this.places(), type);
    }

}
