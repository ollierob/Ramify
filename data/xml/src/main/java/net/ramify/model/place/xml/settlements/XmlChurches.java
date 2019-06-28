package net.ramify.model.place.xml.settlements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(namespace = "http://ramify.net/places", name = "churches")
@XmlRootElement(name = "churches")
public class XmlChurches {

    @XmlElement(name = "church")
    private List<XmlChurch> churches;

}
