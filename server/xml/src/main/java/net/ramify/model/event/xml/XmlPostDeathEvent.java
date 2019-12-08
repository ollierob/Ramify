package net.ramify.model.event.xml;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({XmlBurialEvent.class})
public abstract class XmlPostDeathEvent extends XmlEvent {

}
