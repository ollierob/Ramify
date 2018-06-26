package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.PersonalEvents;
import net.ramify.model.family.Families;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonalAttributes;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface Record {

    @Nonnull
    DateRange date();

    @Nonnull
    Map<Person, PersonalEvents> personalEvents();

    @Nonnull
    Families families();

    Comparator<Record> COMPARE_BY_DATE = Comparator.comparing(Record::date, DateRange.COMPARE_BY_EARLIEST);

    static Map<Person, PersonalEvents> personalEvents(final Set<PersonalAttributes> details) {
        final Map<Person, PersonalEvents> events = new HashMap<>();
        details.forEach(d -> events.put(d.person(), d.inferredEvents()));
        return events;
    }

}
