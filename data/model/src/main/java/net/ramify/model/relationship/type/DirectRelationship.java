package net.ramify.model.relationship.type;

import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @see IndirectRelationship
 */
public interface DirectRelationship extends Relationship {

    @Nonnull
    default List<Relationship> inferredRelationships() {
        return Collections.singletonList(this);
    }

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
