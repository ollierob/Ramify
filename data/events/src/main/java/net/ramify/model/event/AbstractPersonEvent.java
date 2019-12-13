package net.ramify.model.event;

import net.ramify.model.SelfTyped;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.misc.EventWithPlace;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

public abstract class AbstractPersonEvent<T extends AbstractPersonEvent<T>> implements Event, HasPersonId, SelfTyped<T> {

    private final EventId id;
    private final PersonId personId;
    private final DateRange date;

    protected AbstractPersonEvent(final EventId id, final PersonId personId, final DateRange date) {
        this.id = id;
        this.personId = personId;
        this.date = date;
    }

    @Nonnull
    @Override
    public EventId eventId() {
        return id;
    }

    @Nonnull
    @Override
    public PersonId personId() {
        return personId;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @Nonnull
    @CheckReturnValue
    public Event with(final Place place) {
        if (place == null) return this.self();
        return new EventWithPlace<>(this.self(), place);
    }

}
