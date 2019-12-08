package net.ramify.model.event.xml;

import net.ramify.model.event.Event;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.event.type.death.GenericDeathEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "death")
public class XmlDeathEvent extends XmlEvent {

    @Override
    public DeathEvent toEvent(final PersonId personId, final RecordContext context) {
        return new GenericDeathEvent(personId, this.date(context), this.age());
    }

    @Override
    public Set<Event> inferredEvents(final PersonId personId, final RecordContext context) {
        final var birth = this.birth(personId, context);
        return birth == null ? Collections.emptySet() : Collections.singleton(birth);
    }

    protected BirthEvent birth(final PersonId personId, final RecordContext context) {
        final var age = this.age();
        return age == null ? null : new GenericBirth(personId, age.birthDate(this.date(context)));
    }

}
