package net.ramify.model.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.EventHandler;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.Optional;

public abstract class ComposedEvent<T extends Event> implements Event {

    private final T delegate;

    protected ComposedEvent(final T delegate) {
        this.delegate = delegate;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return delegate.date();
    }

    @Nonnull
    @Override
    public PersonId personId() {
        return delegate.personId();
    }

    @Override
    public <R> R handleWith(final EventHandler<R> handler) {
        return delegate.handleWith(handler);
    }

    @Override
    public boolean is(Class<?> type) {
        return delegate.is(type);
    }

    @Override
    public <R extends Event> Optional<R> as(Class<? extends R> clazz) {
        return delegate.as(clazz);
    }

}
