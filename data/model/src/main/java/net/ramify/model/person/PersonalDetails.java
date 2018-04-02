package net.ramify.model.person;

import net.ramify.model.event.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.person.event.PersonalEvents;

import javax.annotation.Nonnull;
import java.util.Set;

public interface PersonalDetails {

    @Nonnull
    Person person();

    @Nonnull
    Set<Event> inferredEventSet(DateRange dateRange);

    default PersonalEvents inferredEvents(final DateRange date) {
        return PersonalEvents.of(this.inferredEventSet(date));
    }

}
