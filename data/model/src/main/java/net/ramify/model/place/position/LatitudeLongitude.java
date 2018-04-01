package net.ramify.model.place.position;

public class LatitudeLongitude implements Position {

    private final double latitude, longitude;

    public LatitudeLongitude(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double latitude() {
        return latitude;
    }

    @Override
    public double longitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "(" + latitude + "," + longitude + "," + this.altitude() + ")";
    }

}
