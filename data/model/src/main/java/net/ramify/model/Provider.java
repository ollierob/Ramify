package net.ramify.model;

import com.google.common.collect.Maps;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

    @Nonnull
    default Map<K, V> getAll(final Set<? extends K> keys) {
        final var map = Maps.<K, V>newHashMapWithExpectedSize(keys.size());
        keys.forEach(key -> map.put(key, this.get(key)));
        return map;
    }

}
