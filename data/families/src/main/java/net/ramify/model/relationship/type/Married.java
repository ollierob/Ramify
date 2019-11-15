package net.ramify.model.relationship.type;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.relationship.AbstractRelationship;
import net.ramify.model.relationship.proto.RelationshipProto;

import javax.annotation.Nonnull;

public class Married extends AbstractRelationship implements DirectRelationship, AffineRelationship {

    public Married(final HasPersonId from, final HasPersonId to) {
        super(from, to);
    }

    @Override
    public Married inverse() {
        return new Married(this.toId(), this.fromId());
    }

    @Nonnull
    @Override
    public RelationshipProto.Relationship.Builder toProtoBuilder() {
        return super.toProtoBuilder().setType(RelationshipProto.Relationship.Type.SPOUSE);
    }

}
