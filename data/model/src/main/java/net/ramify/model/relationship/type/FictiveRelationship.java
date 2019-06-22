package net.ramify.model.relationship.type;

import net.ramify.model.relationship.Relationship;

public interface FictiveRelationship extends Relationship {

    @Override
    default <R> R handleWith(final RelationshipHandler<R> handler) {
        return handler.handle(this);
    }

}
