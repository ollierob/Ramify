package net.ramify.model.person;

import net.ramify.model.event.DateRange;
import net.ramify.model.event.Event;

import javax.annotation.Nonnull;
import java.util.Set;

public interface PersonalDetails {

    @Nonnull
    Person person();

    @Nonnull
    Set<Event> inferredEvents(DateRange dateRange);

}
