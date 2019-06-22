package net.ramify.model.relationship.type;

import com.google.common.collect.Sets;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.Set;

public interface AffineRelationship extends DirectRelationship {

    @Override
    default boolean isDirected() {
        return false;
    }

    @Nonnull
    default Set<PersonId> affines() {
        return Sets.newHashSet(this.from(), this.to());
    }

    @Override
    default <R> R handleWith(final RelationshipHandler<R> handler) {
        return handler.handle(this);
    }

}
