package net.ramify.model.event.xml.person;

import net.ramify.model.event.type.residence.ResidenceEvent;
import net.ramify.model.event.xml.XmlEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "residence")
public class XmlResidenceEvent extends XmlLifeEvent {

    @Override
    public ResidenceEvent build(final PersonId personId, final RecordContext context) {
        return this.eventBuilder(context).toResidence(personId);
    }

}
