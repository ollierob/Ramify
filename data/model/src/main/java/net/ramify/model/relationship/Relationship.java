package net.ramify.model.relationship;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.proto.RelationshipProto;
import net.ramify.model.relationship.type.RelationshipHandler;
import net.ramify.utils.objects.Castable;

import javax.annotation.Nonnull;

public interface Relationship extends Castable<Relationship>, BuildsProto<RelationshipProto.Relationship> {

    @Nonnull
    PersonId fromId();

    @Nonnull
    PersonId toId();

    default boolean has(@Nonnull final PersonId personId) {
        return personId.equals(this.fromId()) || personId.equals(this.toId());
    }

    boolean isDirected();

    <R> R handleWith(final RelationshipHandler<R> handler);

    @Nonnull
    default Relationship inverse() {
        return new InverseRelationship<>(this);
    }

    @Nonnull
    default RelationshipProto.Relationship.Builder toProtoBuilder() {
        return RelationshipProto.Relationship.newBuilder()
                .setFromId(this.fromId().value())
                .setToId(this.toId().value())
                .setDirected(this.isDirected());
    }

    @Nonnull
    @Override
    default RelationshipProto.Relationship toProto() {
        return this.toProtoBuilder().build();
    }

}
