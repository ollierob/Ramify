package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;

public class MarriedCouple extends AbstractRelationship {

    public MarriedCouple(final Person from, final Person to) {
        super(from, to);
    }

    @Nonnull
    public Person first() {
        return super.from();
    }

    @Nonnull
    public Person second() {
        return super.to();
    }

    @Nonnull
    @Override
    public MarriedCouple inverse() {
        return this;
    }

}
