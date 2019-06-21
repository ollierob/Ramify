package net.ramify.model.family.relationship;

import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;

public class Friends extends AbstractRelationship {

    public Friends(final PersonId from, final PersonId to) {
        super(from, to);
    }

    public PersonId first() {
        return this.from();
    }

    public PersonId second() {
        return this.to();
    }

    @Nonnull
    @Override
    public Friends inverse() {
        return this;
    }

    @Override
    public Relationship replace(PersonId from, PersonId to) {
        return new Friends(from, to);
    }

}
