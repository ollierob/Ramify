package net.ramify.model.person;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Death;
import net.ramify.model.event.PersonalEvents;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;

public class Deceased implements PersonalAttributes {

    private final PersonalAttributes delegate;

    public Deceased(final PersonalAttributes delegate) {
        this.delegate = delegate;
    }

    @Nonnull
    @Override
    public Person person() {
        return delegate.person();
    }

    @Nonnull
    @Override
    public DateRange date() {
        return delegate.date();
    }

    @Nonnull
    @Override
    public Name name() {
        return delegate.name();
    }

    @Nonnull
    @Override
    public AgeRange age() {
        return delegate.age();
    }

    @Nonnull
    @Override
    public Gender gender() {
        return delegate.gender();
    }

    @Nonnull
    @Override
    public PersonalEvents inferredEvents() {
        return delegate.inferredEvents().and(new Death(DateRange.before(this.date())));
    }

}
