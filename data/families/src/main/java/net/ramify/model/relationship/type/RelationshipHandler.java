package net.ramify.model.relationship.type;

import net.ramify.model.relationship.Relationship;

public interface RelationshipHandler<R> {

    default R handle(final Relationship relationship) {
        return relationship.handleWith(this);
    }

    R handle(AffineRelationship relationship);

    R handle(CosanguinealRelationship relationship);

    R handle(FictiveRelationship relationship);

    R handle(IndirectRelationship relationship);

    R handle(UnknownRelationship relationship);

}
