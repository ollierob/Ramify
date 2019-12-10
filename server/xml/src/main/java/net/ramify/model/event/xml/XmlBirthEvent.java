package net.ramify.model.event.xml;

import net.ramify.model.event.Event;
import net.ramify.model.event.type.birth.GenericBirthEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "birth")
public class XmlBirthEvent extends XmlEvent {

    @Override
    public Event toEvent(final PersonId personId, final RecordContext context) {
        return new GenericBirthEvent(personId, this.date(context)).with(this.place(context));
    }

    @Override
    public Set<Event> inferredEvents(PersonId personId, RecordContext context) {
        return Collections.emptySet();
    }

}
