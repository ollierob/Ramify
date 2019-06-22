package net.ramify.model.relationship;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.type.RelationshipHandler;

import javax.annotation.Nonnull;
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

    @Override
    public boolean is(final Class<?> type) {
        return inverse.is(type);
    }

    @Override
    public <R extends Relationship> Optional<R> as(final Class<? extends R> clazz) {
        return inverse.as(clazz); //FIXME invoke .as ?
    }

    @Override
    public <R> R handleWith(final RelationshipHandler<R> handler) {
        return inverse.handleWith(handler);
    }

}
