package net.ramify.model.kml;

import net.ramify.model.place.KmlPlacemark;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Document")
public class KmlDocument {

    @XmlElement(name = "name")
    private String name;

    @XmlElements({@XmlElement(name = "Placemark", type = KmlPlacemark.class)})
    private List<? extends KmlDocumentElement> elements;

    public KmlDocument(final String name, final List<? extends KmlDocumentElement> elements) {
        this.name = name;
        this.elements = elements;
    }

}
