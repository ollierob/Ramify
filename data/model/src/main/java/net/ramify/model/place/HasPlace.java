package net.ramify.model.place;

import net.ramify.utils.objects.Castable;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface HasPlace extends HasPlaceId {

    @Nonnull
    Place place();

    @Nonnull
    @Override
    default PlaceId placeId() {
        return this.place().placeId();
    }

    static Optional<Place> place(final Object object) {
        return Castable.cast(object, HasPlace.class).map(p -> p.place());
    }

}
