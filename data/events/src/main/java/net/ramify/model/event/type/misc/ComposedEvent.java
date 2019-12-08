package net.ramify.model.event.type.misc;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.EventHandler;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;

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

    @Nonnull
    @Override
    public String title() {
        return delegate.title();
    }

    @Override
    @Nonnull
    public Optional<Age> givenAge() {
        return delegate.givenAge();
    }

    @Override
    @Nonnull
    public Optional<Age> computedAge() {
        return delegate.computedAge();
    }

    @Override
    public <R> R handleWith(final EventHandler<R> handler) {
        return delegate.handleWith(handler);
    }

    @Override
    public boolean is(final Class<?> type) {
        return delegate.is(type) || Event.super.is(type);
    }

    @Override
    public <R extends Event> Optional<R> as(final Class<? extends R> clazz) {
        //return delegate.as(clazz).or(() -> Event.super.as(clazz));
        final var d = delegate.<R>as(clazz);
        if (d.isPresent()) return d;
        return Event.super.as(clazz);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return delegate.toProtoBuilder();
    }

}
