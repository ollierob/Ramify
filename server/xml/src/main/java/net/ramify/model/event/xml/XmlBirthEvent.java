package net.ramify.model.event.xml;

import net.ramify.model.ParserContext;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.person.PersonId;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "birth")
public class XmlBirthEvent extends XmlEvent {

    @Override
    public Event toEvent(final PersonId personId, final ParserContext context) {
        return new GenericBirth(personId, this.date(context.dateParser()));
    }

}