package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;

public class MarriedCouple extends AbstractRelationship {

    public MarriedCouple(final Person from, final Person to) {
        super(from, to);
    }

    @Nonnull
    @Override
    public MarriedCouple inverse() {
        return this;
    }

    @Override
    public MarriedCouple replace(final Person from, final Person to) {
        return new MarriedCouple(from, to);
    }

}
