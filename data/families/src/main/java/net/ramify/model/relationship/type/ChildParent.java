package net.ramify.model.relationship.type;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;
import net.ramify.model.relationship.proto.RelationshipProto;

import javax.annotation.Nonnull;

public class ChildParent extends AbstractRelationship implements DirectRelationship, CosanguinealRelationship {

    public ChildParent(final HasPersonId child, final HasPersonId parent) {
        super(child, parent);
    }

    public PersonId child() {
        return this.fromId();
    }

    public PersonId parent() {
        return this.toId();
    }

    @Nonnull
    @Override
    public String describeFrom() {
        return "Child";
    }

    @Nonnull
    @Override
    public ParentChild inverse() {
        return new ParentChild(this.parent(), this.child());
    }

    @Nonnull
    @Override
    public RelationshipProto.Relationship.Builder toProtoBuilder() {
        return super.toProtoBuilder().setType(RelationshipProto.Relationship.Type.CHILD_PARENT);
    }

}
