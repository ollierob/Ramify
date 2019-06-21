package net.ramify.model.place;

import net.ramify.model.kml.KmlDocumentElement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Placemark")
public class KmlPlacemark extends KmlDocumentElement {

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

//    @Nonnull
//    public static KmlPlacemark convert(
//            @Nonnull final Place place,
//            @Nonnull final Position position,
//            @Nonnull final Map<String, String> extended) {
//        return new KmlPlacemark(place.name(), KmlPoint.of(position), KmlExtendedData.of(extended));
//    }

}
