package net.ramify.model.place;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;

public class KmlPoint {

    @Deprecated
    private KmlPoint() {
    }

    @XmlElement(name = "coordinates")
    private String coordinates;

    KmlPoint(final String coordinates) {
        this.coordinates = coordinates;
    }

//    public static KmlPoint of(final Position position) {
//        return of(position.toLatitudeAndLongitude());
//    }
//
//    public static KmlPoint of(final LatitudeAndLongitude position) {
//        return new KmlPoint(position.longitude() + "," + position.latitude());
//    }

    @Nonnull
    public String name() {
        return coordinates;
    }

//    @Nonnull
//    public LatitudeAndLongitude toLatitudeAndLongitude() {
//        final String[] split = coordinates.split(",");
//        return new LatitudeAndLongitude(Double.valueOf(split[1]), Double.valueOf(split[0]));
//    }

}
