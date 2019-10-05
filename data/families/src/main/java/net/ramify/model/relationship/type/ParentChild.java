package net.ramify.model.relationship.type;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;
import net.ramify.model.relationship.proto.RelationshipProto;

import javax.annotation.Nonnull;

public class ParentChild extends AbstractRelationship implements CosanguinealRelationship {

    public ParentChild(final HasPersonId parent, final HasPersonId child) {
        super(parent, child);
    }

    public PersonId parent() {
        return this.fromId();
    }

    public PersonId child() {
        return this.toId();
    }

    public ChildParent inverse() {
        return new ChildParent(this.child(), this.parent());
    }

    @Nonnull
    @Override
    public RelationshipProto.Relationship.Builder toProtoBuilder() {
        return super.toProtoBuilder().setDirection(RelationshipProto.Relationship.Direction.TOP_BOTTOM);
    }

}
