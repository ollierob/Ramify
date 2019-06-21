package net.ramify.utils.collections;

import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Predicate;

public class Iterables {

    public static <T> boolean any(final Collection<T> collection, final Predicate<? super T> predicate) {
        if (collection.isEmpty()) return false;
        if (collection instanceof List && collection instanceof RandomAccess) return any((List<T>) collection, predicate);
        return anyIterated(collection, predicate);
    }

    public static <T> boolean any(final List<T> list, final Predicate<? super T> predicate) {
        if (!(list instanceof RandomAccess)) return any((Collection<T>) list, predicate);
        for (int i = 0; i < list.size(); i++) {
            if (predicate.test(list.get(i))) return true;
        }
        return false;
    }

    public static <T> boolean any(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        if (iterable instanceof Collection) return any((Collection<T>) iterable, predicate);
        return anyIterated(iterable, predicate);
    }

    private static <T> boolean anyIterated(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        for (final var element : iterable) {
            if (predicate.test(element)) return true;
        }
        return false;
    }

}
