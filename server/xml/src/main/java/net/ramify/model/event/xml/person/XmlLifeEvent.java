package net.ramify.model.event.xml.person;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({XmlBaptismEvent.class, XmlMarriageEvent.class, XmlResidenceEvent.class})
public abstract class XmlLifeEvent extends XmlPersonEvent {

}
