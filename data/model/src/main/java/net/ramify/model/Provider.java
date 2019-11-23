package net.ramify.model;

import com.google.common.collect.Maps;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public interface Provider<K, V> {

    @CheckForNull
    V get(K key);

    @Nonnull
    default V require(final K key) {
        return this.requireOrThrow(key, k -> new NullPointerException("Value not found for key: " + key));
    }

    @Nonnull
    default <X extends Exception> V requireOrThrow(final K key, final Function<? super K, X> makeException) throws X {
        final var value = this.get(key);
        if (value == null) throw makeException.apply(key);
        return value;
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
