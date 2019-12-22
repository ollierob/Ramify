package net.ramify.model.place.xml.location;

import net.ramify.model.place.position.DefaultPoint;
import net.ramify.model.place.position.Point;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "point")
public class XmlPoint {

    @XmlAttribute(name = "latitude", required = true)
    private double latitude;

    @XmlAttribute(name = "longitude", required = true)
    private double longitude;

    public Point toPoint() {
        return new DefaultPoint(latitude, longitude);
    }

}
