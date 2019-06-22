package net.ramify.model.relationship;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.type.AffineRelationship;
import net.ramify.model.relationship.type.CosanguinealRelationship;
import net.ramify.model.relationship.type.FictiveRelationship;
import net.ramify.model.relationship.type.RelationshipHandler;
import net.ramify.utils.objects.Castable;

import javax.annotation.Nonnull;

public interface Relationship extends Castable<Relationship> {

    @Nonnull
    PersonId from();

    @Nonnull
    PersonId to();

    <R> R handleWith(final RelationshipHandler<R> handler);

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
