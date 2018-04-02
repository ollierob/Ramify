package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;

public class UnknownRelationship extends AbstractRelationship {

    public UnknownRelationship(final Person from, final Person to) {
        super(from, to);
    }

    @Nonnull
    @Override
    public UnknownRelationship inverse() {
        return this;
    }
}
