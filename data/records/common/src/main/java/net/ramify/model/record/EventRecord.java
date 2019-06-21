package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.Histories;
import net.ramify.model.event.PersonalEvents;
import net.ramify.model.family.Family;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * A record of some event.
 *
 * @param <E>
 */
public abstract class EventRecord<E extends Event> implements SingleFamilyRecord {

    private final PersonId person;
    private final E event;

    protected EventRecord(final PersonId person, final E event) {
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
    public Histories histories() {
        return Histories.of(Map.of(person, PersonalEvents.of(person, event)));
    }

    @Nonnull
    @Override
    public Family family() {
        return Family.of(person);
    }

}
