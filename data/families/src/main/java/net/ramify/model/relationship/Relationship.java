package net.ramify.model.relationship;

import net.meerkat.objects.Castable;
import net.ollie.protobuf.BuildsProto;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.proto.RelationshipProto;
import net.ramify.model.relationship.type.RelationshipHandler;

import javax.annotation.Nonnull;
import java.util.List;

public interface Relationship extends Castable<Relationship>, BuildsProto<RelationshipProto.Relationship> {

    @Nonnull
    PersonId fromId();

    @Nonnull
    PersonId toId();

    @Nonnull
    String describeFrom();

    @Nonnull
    default String describeTo() {
        return this.inverse().describeTo();
    }

    default boolean isUnknown() {
        return false;
    }

    default boolean has(@Nonnull final PersonId personId) {
        return personId.equals(this.fromId()) || personId.equals(this.toId());
    }

    boolean isDirected();

    <R> R handleWith(final RelationshipHandler<R> handler);

    @Nonnull
    Relationship inverse();

    @Nonnull
    List<Relationship> inferredRelationships();

    @Nonnull
    default RelationshipProto.Relationship.Builder toProtoBuilder() {
        return RelationshipProto.Relationship.newBuilder()
                .setFromId(this.fromId().value())
                .setToId(this.toId().value())
                .setUnknown(this.isUnknown());
    }

    @Nonnull
    @Override
    default RelationshipProto.Relationship toProto() {
        return this.toProtoBuilder().build();
    }

}
