package net.ramify.model.person;

import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;

public class DelegatedPerson implements Person {

    private final Person person;

    public DelegatedPerson(final Person person) {
        this.person = person;
    }

    @Override
    public PersonId personId() {
        return person.personId();
    }

    @Nonnull
    @Override
    public Gender gender() {
        return person.gender();
    }

    @Nonnull
    @Override
    public Name name() {
        return person.name();
    }

    @Override
    public PersonEventSet events() {
        return person.events();
    }

}
