package net.ramify.model.event.xml.person;

import net.ramify.model.event.type.birth.BaptismEvent;
import net.ramify.model.event.xml.XmlEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "baptism")
public class XmlBaptismEvent extends XmlLifeEvent {

    @Override
    public BaptismEvent build(final PersonId personId, final RecordContext context) {
        return this.eventBuilder(context).toBaptism(personId);
    }

}
