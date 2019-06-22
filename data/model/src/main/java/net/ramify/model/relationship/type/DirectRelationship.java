package net.ramify.model.relationship.type;

import net.ramify.model.relationship.Relationship;

/**
 * @see IndirectRelationship
 */
public interface DirectRelationship extends Relationship {

    default boolean isAffine() {
        return this.is(AffineRelationship.class);
    }

    default boolean isFictive() {
        return this.is(FictiveRelationship.class);
    }

    default boolean isConsanguineal() {
        return this.is(CosanguinealRelationship.class);
    }

}
