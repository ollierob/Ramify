package net.ramify.model.relationship.collection;

import com.google.common.collect.Sets;
import net.meerkat.collections.Collections;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;
import net.ramify.utils.collections.IterableUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasRelationships {

    @Nonnull
    Set<? extends Relationship> relationships();

    default boolean hasRelationship(@Nonnull final Class<? extends Relationship> type) {
        return Collections.any(this.relationships(), r -> r.is(type));
    }

    @Nonnull
    default <R extends Relationship> Set<R> findAllRelationships(@Nonnull final Class<R> type) {
        return IterableUtils.findAll(this.relationships(), type);
    }

    @Nonnull
    default Set<? extends Relationship> relationships(final PersonId person) {
        return Sets.filter(this.relationships(), r -> r.has(person));
    }

    @Nonnull
    default <R extends Relationship> Set<R> relationshipsFrom(final PersonId fromId, final Class<R> type) {
        final var from = Sets.filter(this.relationships(), r -> r.fromId().equals(fromId));
        return IterableUtils.findAll(from, type);
    }

}
