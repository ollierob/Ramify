package net.ramify.model.record;

import net.ramify.model.event.Event;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;
import java.util.Set;

class GenericRecordPerson extends AbstractPerson {

    private final Set<Event> events;

    GenericRecordPerson(PersonId id, Name name, Gender gender, Set<Event> events) {
        super(id, name, gender);
        this.events = events;
    }

    @Nonnull
    @Override
    public Set<? extends Event> events() {
        return events;
    }

}

