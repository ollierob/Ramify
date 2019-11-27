package net.ramify.model.relationship.type;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.proto.RelationshipProto;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class GrandParentChild extends AbstractRelationship implements CosanguinealRelationship {

    public GrandParentChild(final HasPersonId from, final HasPersonId to) {
        super(from, to);
    }

    @Nonnull
    public PersonId grandparent() {
        return this.fromId();
    }

    @Nonnull
    public PersonId grandchild() {
        return this.toId();
    }

    @Nonnull
    @Override
    public String describeFrom() {
        return "Grand-parent";
    }

    @Nonnull
    @Override
    public List<Relationship> inferredRelationships() {
        final PersonId parentId = PersonId.random();
        return Arrays.asList(new ParentChild(this.grandparent(), parentId), new ParentChild(parentId, this.grandchild()));
    }

    @Nonnull
    @Override
    public RelationshipProto.Relationship.Builder toProtoBuilder() {
        return super.toProtoBuilder().setType(RelationshipProto.Relationship.Type.INDIRECT);
    }

}
