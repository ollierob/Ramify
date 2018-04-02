package net.ramify.model.place.position;

import javax.annotation.Nonnull;

public class LatitudeAndLongitude implements Position {

    private final double latitude, longitude;

    public LatitudeAndLongitude(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Nonnull
    @Override
    public String name() {
        return "(" + this.latitude() + "," + this.longitude() + ",0)";
    }

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }

    @Nonnull
    @Override
    @Deprecated
    public LatitudeAndLongitude toLatitudeAndLongitude() {
        return this;
    }

    @Override
    public String toString() {
        return this.name();
    }

}
