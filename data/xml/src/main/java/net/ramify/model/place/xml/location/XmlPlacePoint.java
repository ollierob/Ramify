package net.ramify.model.place.xml.location;

import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.position.Point;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "placePoint")
public class XmlPlacePoint implements Point, HasPlaceId {

    @XmlAttribute(name = "id", required = true)
    private String placeId;

    @XmlAttribute(name = "latitude", required = true)
    private double latitude;

    @XmlAttribute(name = "longitude", required = true)
    private double longitude;

    @Deprecated
    XmlPlacePoint() {
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return new PlaceId(placeId);
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
