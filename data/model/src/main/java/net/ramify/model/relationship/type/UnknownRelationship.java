package net.ramify.model.relationship.type;

import net.ramify.model.relationship.Relationship;

public interface UnknownRelationship extends Relationship {

    @Override
    default boolean isDirected() {
        return false;
    }

    @Override
    default <R> R handleWith(final RelationshipHandler<R> handler) {
        return handler.handle(this);
    }

}
