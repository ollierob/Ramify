package net.ramify.model.relationship;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.proto.RelationshipProto;
import net.ramify.model.relationship.type.RelationshipHandler;
import net.ramify.utils.collections.ListUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class InverseRelationship<R extends Relationship> implements Relationship {

    private final R inverse;

    public InverseRelationship(final R inverse) {
        this.inverse = inverse;
    }

    @Nonnull
    @Override
    public PersonId fromId() {
        return inverse.toId();
    }

    @Nonnull
    @Override
    public PersonId toId() {
        return inverse.fromId();
    }

    @Override
    public boolean isDirected() {
        return inverse.isDirected();
    }

    @Override
    public R inverse() {
        return inverse;
    }

    @Nonnull
    @Override
    public String describeFrom() {
        return inverse.describeTo();
    }

    @Nonnull
    @Override
    public String describeTo() {
        return inverse.describeFrom();
    }

    @Override
    public boolean is(final Class<?> type) {
        return inverse.is(type);
    }

    @Override
    public <R extends Relationship> Optional<R> as(final Class<? extends R> clazz) {
        return inverse.as(clazz); //FIXME invoke .as ?
    }

    @Nonnull
    @Override
    public List<Relationship> inferredRelationships() {
        return ListUtils.eagerlyTransform(inverse.inferredRelationships(), Relationship::inverse);
    }

    @Override
    public <R> R handleWith(final RelationshipHandler<R> handler) {
        return inverse.handleWith(handler);
    }

    @Nonnull
    @Override
    public RelationshipProto.Relationship.Builder toProtoBuilder() {
        return inverse.toProtoBuilder();
    }

}
