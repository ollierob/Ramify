package net.ramify.model.family.relationship;

import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;

public class MarriedCouple extends AbstractRelationship {

    public MarriedCouple(final PersonId from, final PersonId to) {
        super(from, to);
    }

    @Nonnull
    @Override
    public MarriedCouple inverse() {
        return this;
    }

    @Override
    public MarriedCouple replace(final PersonId from, final PersonId to) {
        return new MarriedCouple(from, to);
    }

}
