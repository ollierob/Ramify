package net.ramify.model.event.type.misc;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public abstract class ComposedEvent<T extends PersonEvent> implements PersonEvent {

    private final T delegate;

    protected ComposedEvent(final T delegate) {
        this.delegate = delegate;
    }

    protected T delegate() {
        return delegate;
    }

    @Nonnull
    @Override
    public EventId eventId() {
        return delegate.eventId();
    }

    @Nonnull
    @Override
    public PersonId personId() {
        return delegate.personId();
    }

    @Nonnull
    @Override
    public DateRange date() {
        return delegate.date();
    }

    @Nonnull
    @Override
    public Set<PersonId> personIds() {
        return delegate.personIds();
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

    @Nonnull
    @Override
    public Optional<String> occupation() {
        return delegate.occupation();
    }

    @Nonnull
    @Override
    public Optional<String> description() {
        return delegate.description();
    }

    @Override
    public boolean isInferred() {
        return delegate.isInferred();
    }

    @Override
    public boolean is(final Class<?> type) {
        return delegate.is(type) || PersonEvent.super.is(type);
    }

    @Override
    public <R extends PersonEvent> Optional<R> as(final Class<? extends R> clazz) {
        //return delegate.as(clazz).or(() -> Event.super.as(clazz));
        final var d = delegate.<R>as(clazz);
        if (d.isPresent()) return d;
        return PersonEvent.super.as(clazz);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return delegate.toProtoBuilder();
    }

}
