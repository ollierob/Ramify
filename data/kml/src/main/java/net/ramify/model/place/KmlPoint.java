package net.ramify.model.place;

import javax.xml.bind.annotation.XmlElement;

public class KmlPoint implements Position {

    @Deprecated
    private KmlPoint() {
    }

    @XmlElement(name = "coordinates")
    private String coordinates;

    KmlPoint(final String coordinates) {
        this.coordinates = coordinates;
    }

    public static KmlPoint of(final Position position) {
        return position instanceof KmlPoint
                ? (KmlPoint) position
                : new KmlPoint(position.longitude() + "," + position.latitude() + "," + position.altitude());
    }

    @Override
    public double latitude() {
        return Double.valueOf(coordinates.split(",")[1]);
    }

    @Override
    public double longitude() {
        return Double.valueOf(coordinates.split(",")[0]);
    }

    @Override
    public double altitude() {
        final String[] split = coordinates.split(",");
        return split.length == 2 ? Double.valueOf(split[2]) : 0;
    }

}
