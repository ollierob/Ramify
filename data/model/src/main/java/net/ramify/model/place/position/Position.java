package net.ramify.model.place.position;

public interface Position {

    double latitude();

    double longitude();

    default double altitude() {
        return 0;
    }

    static LatitudeAndLongitude of(final double latitude, final double longitude) {
        return new LatitudeAndLongitude(latitude, longitude);
    }

}
