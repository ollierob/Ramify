package net.ramify.model.relationship.type;

import com.google.common.collect.Sets;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Affinity
 */
public interface AffineRelationship extends Relationship {

    @Override
    default boolean isDirected() {
        return false;
    }

    @Nonnull
    default Set<PersonId> affines() {
        return Sets.newHashSet(this.fromId(), this.toId());
    }

    @Override
    default <R> R handleWith(final RelationshipHandler<R> handler) {
        return handler.handle(this);
    }

}
