package net.ramify.model.family.relationship;

import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;

public abstract class AbstractRelationship implements Relationship {

    private final PersonId from, to;

    protected AbstractRelationship(final PersonId from, final PersonId to) {
        this.from = from;
        this.to = to;
    }

    @Nonnull
    public PersonId from() {
        return from;
    }

    @Nonnull
    public PersonId to() {
        return to;
    }

}
