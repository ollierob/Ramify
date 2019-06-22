package net.ramify.model.relationship.type;

public interface FictiveRelationship extends DirectRelationship {

    @Override
    default <R> R handleWith(final RelationshipHandler<R> handler) {
        return handler.handle(this);
    }

}
