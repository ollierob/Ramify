package net.ramify.model.place;

import javax.annotation.Nonnull;
import java.util.function.Function;

public interface HasPlaceId {

    @Nonnull
    PlaceId placeId();

    default Place place(final Function<? super PlaceId, ? extends Place> lookup) {
        return lookup.apply(this.placeId());
    }

}
