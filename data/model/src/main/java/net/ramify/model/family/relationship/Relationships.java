package net.ramify.model.family.relationship;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface Relationships {

    @Nonnull
    Set<Relationship> relationships();

    @Nonnull
    default <R extends Relationship> Stream<R> find(final Class<R> clazz) {
        return this.relationships()
                .stream()
                .map(relationship -> relationship.as(clazz))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

}
