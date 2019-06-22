package net.ramify.model.relationship.type;

import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @see DirectRelationship
 */
public interface IndirectRelationship extends Relationship {

    @Nonnull
    List<Relationship> relationships();

    @Override
    default <R> R handleWith(final RelationshipHandler<R> handler) {
        return handler.handle(this);
    }

}
