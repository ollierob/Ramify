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

    public static <F, T> Set<T> transformIgnoreNull(final Collection<F> collection, final Function<? super F, ? extends T> transform, final IntFunction<Set<T>> createSet) {
        return transform(collection, transform, true, createSet);
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

    public static <T> Set<T> with(final Set<T> set, final T element) {
        if (set.isEmpty()) return Collections.singleton(element);
        if (set.contains(element)) return set;
        final var copy = Sets.newHashSet(set);
        copy.add(element);
        return copy;
    }

    public static <T> Set<T> without(final Set<T> set, final T element) {
        if (set.isEmpty() || !set.contains(element)) return set;
        if (set.size() == 1) return Collections.emptySet();
        final var newSet = Sets.newHashSet(set);
        newSet.remove(element);
        return newSet;
    }

}
