package net.ramify.model.relationship.type;

import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public interface UnknownRelationship extends DirectRelationship {

    @Override
    default boolean isDirected() {
        return false;
    }

    @Override
    default boolean isUnknown() {
        return true;
    }

    @Nonnull
    @Override
    default List<Relationship> inferredRelationships() {
        return Collections.emptyList();
    }

    @Override
    default <R> R handleWith(final RelationshipHandler<R> handler) {
        return handler.handle(this);
    }

}
