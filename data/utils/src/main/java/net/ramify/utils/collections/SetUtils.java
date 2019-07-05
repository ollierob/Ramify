package net.ramify.utils.collections;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

public class SetUtils {

    public static <F, T> Set<T> transform(final Collection<F> collection, final Function<? super F, ? extends T> transform) {
        return transform(collection, transform, false);
    }

    public static <F, T> Set<T> transformIgnoreNull(final Collection<F> collection, final Function<? super F, ? extends T> transform) {
        return transform(collection, transform, true);
    }

    private static <F, T> Set<T> transform(final Collection<F> collection, final Function<? super F, ? extends T> transform, final boolean ignoreNull) {
        if (collection.isEmpty()) return Collections.emptySet();
        final var set = Sets.<T>newHashSetWithExpectedSize(collection.size());
        for (final var element : collection) {
            final var transformed = transform.apply(element);
            if (transformed == null & ignoreNull) continue;
            set.add(transformed);
        }
        return set;
    }

    public static <T> Set<T> union(final Set<T> left, final Set<T> right) {
        if (left.isEmpty()) return right;
        if (right.isEmpty()) return left;
        return Sets.union(left, right);
    }

    public static <T> Set<T> asSet(final Collection<T> collection) {
        return collection instanceof Set
                ? (Set<T>) collection
                : Sets.newHashSet(collection);
    }

}
