package net.ramify.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public interface Provider<K, V> {

    @CheckForNull
    V get(K key);

    @Nonnull
    default V require(final K key) {
        return Objects.requireNonNull(this.get(key));
    }

    @Nonnull
    default Optional<V> maybeGet(final K key) {
        return Optional.ofNullable(this.get(key));
    }

}
