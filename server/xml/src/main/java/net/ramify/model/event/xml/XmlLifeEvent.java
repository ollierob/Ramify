package net.ramify.model.event.xml;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({XmlMarriageEvent.class, XmlResidenceEvent.class})
public abstract class XmlLifeEvent extends XmlEvent {

}
