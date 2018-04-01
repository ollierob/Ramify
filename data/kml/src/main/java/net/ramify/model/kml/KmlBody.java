package net.ramify.model.kml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class KmlBody {

    @XmlElement
    private KmlDocument document;

}
