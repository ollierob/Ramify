package net.ramify.model.place;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;

@XmlRootElement(name = "Placemark")
public class KmlPlacemark implements Place {

    @XmlElement(name = "Point")
    private KmlPoint point;

    @Nonnull
    @Override
    public Optional<KmlPoint> position() {
        return Optional.of(point);
    }

}
