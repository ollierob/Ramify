package net.ramify.strategy.join.relationship;

import net.ramify.model.family.relationship.Relationship;
import net.ramify.strategy.merge.person.PersonMerge;

import javax.annotation.Nonnull;

class SamePerson implements RelationshipJoin {

    private final PersonMerge personMerge;

    SamePerson(final PersonMerge personMerge) {
        this.personMerge = personMerge;
    }

    @Nonnull
    @Override
    public boolean join(final Relationship r1, final Relationship r2) {
        return personMerge.canMerge(r1.from(), r2.from())
                || personMerge.canMerge(r1.from(), r2.to())
                || personMerge.canMerge(r2.to(), r2.from())
                || personMerge.canMerge(r2.to(), r2.to());
    }

}
