package net.ramify.model.kml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "kml")
public class KmlBody {

    @XmlElement(name = "Document")
    private KmlDocument document;

    public KmlBody(final KmlDocument document) {
        this.document = document;
    }

}
