package net.ramify.model.kml;

import net.ramify.model.place.KmlPlacemark;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Document")
public class KmlDocument {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "Placemark")
    private List<KmlPlacemark> placemarks;

}
