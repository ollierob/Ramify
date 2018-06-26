package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.family.Family;
import net.ramify.model.person.Person;
import net.ramify.model.person.event.PersonalEvents;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * A record of some event.
 *
 * @param <E>
 */
public abstract class EventRecord<E extends Event> implements Record {

    private final Person person;
    private final E event;

    protected EventRecord(final Person person, final E event) {
        this.person = person;
        this.event = event;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return event.date();
    }

    @Nonnull
    @Override
    public Map<Person, PersonalEvents> personalEvents() {
        return Map.of(person, PersonalEvents.of(event));
    }

    @Nonnull
    @Override
    public Family family() {
        return Family.of(person);
    }

}
