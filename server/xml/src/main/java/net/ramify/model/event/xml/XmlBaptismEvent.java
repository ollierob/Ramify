package net.ramify.model.event.xml;

import net.ramify.model.event.type.birth.BaptismEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "baptism")
public class XmlBaptismEvent extends XmlLifeEvent {

    @Override
    public BaptismEvent toEvent(final PersonId personId, final RecordContext context) {
        return this.eventBuilder(context).toBaptism(personId);
    }

}
