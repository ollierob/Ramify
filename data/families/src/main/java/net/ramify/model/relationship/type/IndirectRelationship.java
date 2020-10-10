package net.ramify.model.relationship.type;

import net.ramify.model.relationship.Relationship;

/**
 * @see DirectRelationship
 */
public interface IndirectRelationship extends Relationship {

    @Override
    default <R> R handleWith(final RelationshipHandler<R> handler) {
        return handler.handle(this);
    }

}
