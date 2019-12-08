package net.ramify.model.event.xml;

import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.type.death.GenericDeathEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "death")
public class XmlDeathEvent extends XmlEvent {

    @Override
    public DeathEvent toEvent(final PersonId personId, final RecordContext context) {
        return new GenericDeathEvent(personId, this.date(context), this.age()).with(this.place(context));
    }

}
