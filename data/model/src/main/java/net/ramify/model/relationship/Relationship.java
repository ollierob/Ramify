package net.ramify.model.relationship;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.type.RelationshipHandler;
import net.ramify.utils.objects.Castable;

import javax.annotation.Nonnull;

public interface Relationship extends Castable<Relationship> {

    @Nonnull
    PersonId from();

    @Nonnull
    PersonId to();

    default boolean has(final PersonId personId) {
        return personId.equals(this.from()) || personId.equals(this.to());
    }

    <R> R handleWith(final RelationshipHandler<R> handler);

    boolean isDirected();

    @Nonnull
    default Relationship inverse() {
        return new InverseRelationship<>(this);
    }

}
