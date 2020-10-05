package net.ramify.utils.collections;

import com.google.common.collect.ImmutableMap;
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

    public static <K, V1, V2> Map<K, V2> eagerlyTransformValues(final Map<? extends K, ? extends V1> map, final Function<? super V1, ? extends V2> transform) {
        if (map.isEmpty()) return ImmutableMap.of();
        final var out = Maps.<K, V2>newHashMapWithExpectedSize(map.size());
        map.forEach((k, v1) -> {
            final var v2 = transform.apply(v1);
            out.put(k, v2);
        });
        return out;
    }

}
