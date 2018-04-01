package net.ramify.model.place;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Placemark")
public class KmlPlacemark implements Location {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "Point")
    private KmlPoint point;

    @XmlElement(name = "ExtendedData")
    private KmlExtendedData extendedData;

    public KmlPlacemark(final String name, final KmlPoint point, KmlExtendedData extendedData) {
        this.name = name;
        this.point = point;
        this.extendedData = extendedData;
    }

    @Nonnull
    @Override
    public String name() {
        return name;
    }

    public static KmlPlacemark convert(final Location location) {
        return location instanceof KmlPlacemark
                ? (KmlPlacemark) location
                : new KmlPlacemark(location.name(), KmlPoint.of(location.position()), null);

    }

    @Nonnull
    public KmlPoint position() {
        return point;
    }

}
