package net.ramify.model.relationship.type;

import net.ramify.model.relationship.proto.RelationshipProto;

import javax.annotation.Nonnull;

public interface FictiveRelationship extends DirectRelationship {

    @Override
    default <R> R handleWith(final RelationshipHandler<R> handler) {
        return handler.handle(this);
    }

    @Nonnull
    @Override
    default RelationshipProto.Relationship.Builder toProtoBuilder() {
        return DirectRelationship.super.toProtoBuilder().setType(RelationshipProto.Relationship.Type.FICTIVE);
    }

}
