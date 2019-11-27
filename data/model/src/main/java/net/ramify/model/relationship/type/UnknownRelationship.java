package net.ramify.model.relationship.type;

import javax.annotation.Nonnull;

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
    default String describeFrom() {
        return "Unknown";
    }

    @Nonnull
    @Override
    default String describeTo() {
        return this.describeFrom();
    }

    @Override
    default <R> R handleWith(final RelationshipHandler<R> handler) {
        return handler.handle(this);
    }

}
