package net.ramify.model.place;

import net.ramify.model.place.position.Position;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;
import java.util.Optional;

@XmlRootElement(name = "Placemark")
public class KmlPlacemark implements Location {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "Point")
    private KmlPoint point;

    @XmlElement(name = "ExtendedData")
    private KmlExtendedData extendedData;

    public KmlPlacemark(final String name, final KmlPoint point, final KmlExtendedData extendedData) {
        this.name = name;
        this.point = point;
        this.extendedData = extendedData;
    }

    @Nonnull
    @Override
    public String name() {
        return name;
    }

    @CheckForNull
    public static KmlPlacemark convert(final Location location, final Map<String, String> extended) {
        if (location instanceof KmlPlacemark) {
            return (KmlPlacemark) location;
        }
        final Optional<? extends Position> position = location.position();
        if (!position.isPresent()) {
            return null;
        }
        return new KmlPlacemark(location.name(), KmlPoint.of(position.get()), KmlExtendedData.of(extended));
    }

    @Nonnull
    public Optional<KmlPoint> position() {
        return Optional.of(point);
    }

}
