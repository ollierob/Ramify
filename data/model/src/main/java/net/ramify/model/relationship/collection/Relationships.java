package net.ramify.model.relationship.collection;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.proto.RelationshipProto;

import javax.annotation.Nonnull;

public interface Relationships extends HasRelationships, BuildsProto<RelationshipProto.RelationshipList> {

    @Nonnull
    default RelationshipProto.RelationshipList.Builder toProtoBuilder() {
        return RelationshipProto.RelationshipList.newBuilder()
                .addAllRelationship(Iterables.transform(this.relationships(), Relationship::toProto));
    }

    @Nonnull
    @Override
    default RelationshipProto.RelationshipList toProto() {
        return this.toProtoBuilder().build();
    }

}
