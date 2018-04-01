package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;

public class Friends extends AbstractRelationship {

    public Friends(final Person from, final Person to) {
        super(from, to);
    }

    public Person first() {
        return this.from();
    }

    public Person second() {
        return this.to();
    }

    @Nonnull
    @Override
    public Friends inverse() {
        return this;
    }
}
