package net.ramify.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Objects;

public interface Provider<K, V> {

    @CheckForNull
    V get(K key);

    @Nonnull
    default V require(final K key) {
        return Objects.requireNonNull(this.get(key));
    }

}
