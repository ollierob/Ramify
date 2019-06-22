package net.ramify.model;

import net.ramify.utils.collections.IterableUtils;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public interface Has<T extends Castable<? super T>> {

    @Nonnull
    Set<? extends T> values();

    default boolean has(@Nonnull final Class<? extends T> type) {
        return IterableUtils.any(this.values(), event -> event.is(type));
    }

    @Nonnull
    default <R extends T> Set<R> findAll(@Nonnull final Class<R> type) {
        return this.values()
                .stream()
                .map(e -> e.as(type).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

}
