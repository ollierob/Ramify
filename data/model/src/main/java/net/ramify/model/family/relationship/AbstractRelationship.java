package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;

public abstract class AbstractRelationship implements Relationship {

    private final Person from, to;

    protected AbstractRelationship(final Person from, final Person to) {
        this.from = from;
        this.to = to;
    }

    @Nonnull
    public Person from() {
        return from;
    }

    @Nonnull
    public Person to() {
        return to;
    }

}
