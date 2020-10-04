package net.ramify.model.person;

import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;

public abstract class AbstractPerson implements PersonBuilder {

    private final PersonId id;
    private final Name name;
    private final Gender gender;

    protected AbstractPerson(final PersonId id, final Name name, final Gender gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    @Override
    public PersonId personId() {
        return id;
    }

    @Override
    public Name name() {
        return name;
    }

    @Override
    public Gender gender() {
        return gender;
    }

    @Nonnull
    @Override
    public abstract PersonEventSet events();

}
