package net.ramify.model.place;

import net.meerkat.objects.Castable;
import net.ramify.model.place.provider.PlaceProvider;

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

    @Nonnull
    @Override
    @Deprecated
    default Place resolvePlace(final PlaceProvider lookup) {
        return this.place();
    }

    @Nonnull
    static Optional<Place> getPlace(final Object object) {
        return Castable.cast(object, HasPlace.class).map(HasPlace::place);
    }

}
