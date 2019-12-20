package net.ramify.model.event.xml;

import net.ramify.model.event.type.will.ProbateEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "probate")
public class XmlProbateEvent extends XmlPostDeathEvent {

    @Override
    public ProbateEvent build(final PersonId personId, final RecordContext context) {
        return this.eventBuilder(context).toProbate(personId);
    }

}
