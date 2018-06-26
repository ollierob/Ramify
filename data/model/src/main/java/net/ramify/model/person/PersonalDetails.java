package net.ramify.model.person;

import net.ramify.model.event.Event;
import net.ramify.model.event.PersonalEvents;

import javax.annotation.Nonnull;
import java.util.Set;

public interface PersonalDetails extends HasPerson {

    @Nonnull
    @Override
    Person person();

    @Nonnull
    Set<Event> inferredEventSet();

    default PersonalEvents inferredEvents() {
        return PersonalEvents.of(this.inferredEventSet());
    }

}
