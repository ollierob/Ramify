package net.ramify.model;

import java.util.Optional;

public interface Castable<T> {

    static <T> Optional<T> cast(final Object object, final Class<? extends T> clazz) {
        return clazz.isAssignableFrom(object.getClass())
                ? Optional.of(clazz.cast(object))
                : Optional.empty();
    }

    default <R extends T> Optional<R> cast(final Class<? extends R> clazz) {
        return cast(this, clazz);
    }

}
