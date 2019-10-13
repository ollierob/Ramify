package net.ramify.model.event.xml;

import net.ramify.model.ParserContext;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.death.GenericDeath;
import net.ramify.model.person.PersonId;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "death")
public class XmlDeathEvent extends XmlEvent {

    @Override
    public Event toEvent(PersonId personId, ParserContext context) {
        return new GenericDeath(personId, this.date(context.dateParser()));
    }

}
