package net.ramify.model.event;

import net.ramify.model.SelfTyped;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

public abstract class AbstractPersonEvent<T extends AbstractPersonEvent<T>> implements EventWithProperties, HasPersonId, SelfTyped<T> {

    private final EventId id;
    private final PersonId personId;
    private final EventProperties properties;

    protected AbstractPersonEvent(final EventId id, final PersonId personId, final EventProperties properties) {
        this.id = id;
        this.personId = personId;
        this.properties = properties;
    }

    @Nonnull
    @Override
    public EventProperties properties() {
        return properties;
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
    @CheckReturnValue
    public abstract Event with(Place place);

}
