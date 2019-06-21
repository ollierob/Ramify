package net.ramify.model.person;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.PersonalEvents;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;

public abstract class DelegatedPersonalAttributes implements PersonalAttributes {

    private final PersonalAttributes delegate;

    protected DelegatedPersonalAttributes(final PersonalAttributes delegate) {
        this.delegate = delegate;
    }

    @Override
    @Nonnull
    public PersonId personId() {
        return delegate.personId();
    }

    @Override
    @Nonnull
    public DateRange date() {
        return delegate.date();
    }

    @Override
    @Nonnull
    public Name name() {
        return delegate.name();
    }

    @Override
    @Nonnull
    public AgeRange age() {
        return delegate.age();
    }

    @Override
    @Nonnull
    public Gender gender() {
        return delegate.gender();
    }

    @Override
    @Nonnull
    public PersonalEvents inferredEvents() {
        return delegate.inferredEvents();
    }
}
