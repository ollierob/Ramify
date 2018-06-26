package net.ramify.model.record;

import net.ramify.model.event.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.family.Family;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonalDetails;
import net.ramify.model.person.event.PersonalEvents;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface Record {

    @Nonnull
    DateRange date();

    @Nonnull
    Map<Person, PersonalEvents> personalEvents();

    @Nonnull
    Family family();

    @Nonnull
    default Set<Person> people() {
        return this.personalEvents().keySet(); //And people in family
    }

    @Nonnull
    default Set<Event> events() {
        return this.personalEvents()
                .values()
                .stream()
                .flatMap(PersonalEvents::eventStream)
                .collect(Collectors.toSet());
    }

    Comparator<Record> COMPARE_BY_DATE = Comparator.comparing(Record::date, DateRange.COMPARE_BY_EARLIEST);

    static Map<Person, PersonalEvents> personalEvents(final Set<PersonalDetails> details) {
        final Map<Person, PersonalEvents> events = new HashMap<>();
        details.forEach(d -> events.put(d.person(), d.inferredEvents()));
        return events;
    }

}
