package net.ramify.model.event.xml;

import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.type.burial.BurialEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "burial")
public class XmlBurialEvent extends XmlPostDeathEvent {

    @XmlAttribute(name = "plot")
    private String plot;

    @Override
    public BurialEvent toEvent(final PersonId personId, final RecordContext context) {
        return this.eventBuilder(context).toBurial(personId);
    }

    @Override
    protected Set<PersonEvent> inferredEvents(final PersonId personId, final RecordContext context) {
        return super.inferredEvents(personId, context); //FIXME infer "recent" death
    }

}
