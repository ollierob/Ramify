package net.ramify.model.place;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;

@XmlRootElement(name = "Placemark")
public class KmlPlacemark implements Place {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "Point")
    private KmlPoint point;

    @XmlElement(name = "ExtendedData")
    private KmlExtendedData extendedData;

    @Nonnull
    @Override
    public String name() {
        return name;
    }

    @Nonnull
    @Override
    public Optional<KmlPoint> position() {
        return Optional.of(point);
    }

}
