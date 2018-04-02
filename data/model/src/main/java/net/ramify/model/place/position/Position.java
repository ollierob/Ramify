package net.ramify.model.place.position;

import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public interface Position extends Place {

    @Nonnull
    LatitudeAndLongitude toLatitudeAndLongitude();

    static LatitudeAndLongitude of(final double latitude, final double longitude) {
        return new LatitudeAndLongitude(latitude, longitude);
    }

}
