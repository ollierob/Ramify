package net.ramify.model.record.civil.uk;

import net.ramify.model.event.Event;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

class GeneralRegisterRecordPerson extends AbstractPerson {

    private final Set<Event> events;

    GeneralRegisterRecordPerson(PersonId id, Name name, Gender gender, Event event) {
        this(id, name, gender, event == null ? Collections.emptySet() : Collections.singleton(event));
    }

    GeneralRegisterRecordPerson(PersonId id, Name name, Gender gender, Set<Event> events) {
        super(id, name, gender);
        this.events = events;
    }

    @Nonnull
    @Override
    public Set<? extends Event> events() {
        return events;
    }

}

