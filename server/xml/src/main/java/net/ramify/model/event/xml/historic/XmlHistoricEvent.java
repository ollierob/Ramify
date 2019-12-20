package net.ramify.model.event.xml.historic;

import net.ramify.model.event.xml.XmlEvent;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "historicEvent")
public class XmlHistoricEvent extends XmlEvent {

    @XmlAttribute(name = "title", required = true)
    private String title;

    @XmlValue
    private String description;

}
