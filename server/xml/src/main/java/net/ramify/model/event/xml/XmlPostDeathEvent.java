package net.ramify.model.event.xml;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

@XmlTransient
@XmlSeeAlso({XmlBurialEvent.class, XmlProbateEvent.class})
public abstract class XmlPostDeathEvent extends XmlEvent {

    @Override
    protected Set<Event> inferredEvents(final PersonId personId, final RecordContext context) {
        final var inferred = super.inferredEvents(personId, context);
        inferred.add(this.inferDeath(personId, context));
        return inferred;
    }

    protected DeathEvent inferDeath(final PersonId personId, final RecordContext context) {
        return this.eventBuilder(this.randomEventId(), BeforeDate.strictlyBefore(context.recordDate()))
                .setInferred(true)
                .toDeath(personId);
    }

}
