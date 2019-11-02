package net.ramify.model.event.xml;

import net.ramify.model.event.Event;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.event.type.residence.Residence;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "residence")
public class XmlResidenceEvent extends XmlEvent {

    @Override
    public Residence toEvent(final PersonId personId, final RecordContext context) {
        return new GenericResidence(
                personId,
                this.date(context),
                context.places().require(this.placeId()));
    }

    @Override
    public Set<Event> inferredEvents(final PersonId personId, final RecordContext context) {
        return Collections.emptySet();
    }

}
