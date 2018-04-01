package net.ramify.model;

import java.util.Optional;

public interface Castable<T> {

    default <R extends T> Optional<R> cast(final Class<? extends R> clazz) {
        return clazz.isAssignableFrom(this.getClass())
                ? Optional.of(clazz.cast(this))
                : Optional.empty();
    }

}
