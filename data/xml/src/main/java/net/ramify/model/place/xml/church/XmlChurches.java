package net.ramify.model.place.xml.church;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(namespace = "http://ramify.net", name = "churches")
@XmlRootElement(name = "churches")
public class XmlChurches {

    @XmlElement(name = "church")
    private List<XmlChurch> churches;

}
