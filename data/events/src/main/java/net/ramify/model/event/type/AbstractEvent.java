package net.ramify.model.event.type;

import net.ramify.model.SelfTyped;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.misc.EventWithPlace;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.PlaceId;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

public abstract class AbstractEvent<T extends AbstractEvent<T>> implements Event, SelfTyped<T> {

    private final PersonId personId;
    private final DateRange date;

    protected AbstractEvent(final PersonId personId, final DateRange date) {
        this.personId = personId;
        this.date = date;
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
    public Event with(final PlaceId place) {
        if (place == null) return this;
        return new EventWithPlace<T>(this.self(), place);
    }

}
