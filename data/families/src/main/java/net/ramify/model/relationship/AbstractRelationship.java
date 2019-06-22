package net.ramify.model.relationship;

import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;

public abstract class AbstractRelationship implements Relationship {

    private final PersonId from, to;

    protected AbstractRelationship(PersonId from, PersonId to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public PersonId from() {
        return from;
    }

    @Nonnull
    @Override
    public PersonId to() {
        return to;
    }
}

