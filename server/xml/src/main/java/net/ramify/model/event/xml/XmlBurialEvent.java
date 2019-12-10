package net.ramify.model.event.xml;

import net.ramify.model.event.Event;
import net.ramify.model.event.type.burial.GenericBurial;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "burial")
public class XmlBurialEvent extends XmlPostDeathEvent {

    @Override
    public Event toEvent(final PersonId personId, final RecordContext context) {
        return new GenericBurial(personId, this.date(context), this.age(), this.place(context));
    }

    @Override
    protected Set<Event> inferredEvents(final PersonId personId, final RecordContext context) {
        return super.inferredEvents(personId, context); //FIXME infer "recent" death
    }

}
