package net.ramify.model.event.xml.person;

import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.xml.XmlEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "death")
public class XmlDeathEvent extends XmlPersonEvent {

    @XmlAttribute(name = "cause")
    private String cause;

    @Override
    public DeathEvent build(final PersonId personId, final RecordContext context) {
        return this.eventBuilder(context).toDeath(personId, cause);
    }

}
