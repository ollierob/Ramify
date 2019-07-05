package net.ramify.utils.collections;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;

public class SetUtils {

    public static <F, T> Set<T> transform(final Collection<F> collection, final Function<? super F, ? extends T> transform) {
        return transform(collection, transform, Sets::newHashSetWithExpectedSize);
    }

    public static <F, T> Set<T> transform(final Collection<F> collection, final Function<? super F, ? extends T> transform, final IntFunction<Set<T>> createSet) {
        return transform(collection, transform, false, createSet);
    }

    public static <F, T> Set<T> transformIgnoreNull(final Collection<F> collection, final Function<? super F, ? extends T> transform) {
        return transform(collection, transform, true, Sets::newHashSetWithExpectedSize);
    }

    private static <F, T> Set<T> transform(final Collection<F> collection, final Function<? super F, ? extends T> transform, final boolean ignoreNull, final IntFunction<Set<T>> createSet) {
        if (collection.isEmpty()) return Collections.emptySet();
        final var set = createSet.apply(collection.size());
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
