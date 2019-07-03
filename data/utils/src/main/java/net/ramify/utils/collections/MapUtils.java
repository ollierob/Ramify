package net.ramify.utils.collections;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class MapUtils {

    @Nonnull
    public static <K, V, T extends V> Map<K, V> toMapLastKey(final Collection<T> values, final Function<? super T, ? extends K> toKey) {
        if (values.isEmpty()) return Collections.emptyMap();
        final var map = Maps.<K, V>newHashMapWithExpectedSize(values.size());
        values.forEach(value -> map.put(toKey.apply(value), value));
        return map;
    }

}
