package net.ramify.model.place.position;

public class DefaultPoint implements Point {

    private final double latitude;
    private final double longitude;

    public DefaultPoint(double latitude, double longitude) {
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

}
