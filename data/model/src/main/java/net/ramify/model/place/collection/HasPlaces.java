package net.ramify.model.place.collection;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.utils.collections.IterableUtils;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasPlaces {

    @Nonnull
    Set<? extends Place> places();

    @Nonnull
    default PlaceIds placeIds() {
        return PlaceIds.of(SetUtils.transform(this.places(), Place::placeId));
    }

    default boolean hasPlace(final PlaceId placeId) {
        return IterableUtils.any(this.places(), p -> placeId.equals(p.placeId()));
    }

    default <P extends Place> boolean hasPlace(final Class<P> type) {
        return IterableUtils.has(this.places(), type);
    }

}
