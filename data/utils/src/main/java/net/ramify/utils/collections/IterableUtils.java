package net.ramify.utils.collections;

import com.google.common.collect.Streams;
import net.ramify.utils.objects.Castable;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.RandomAccess;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IterableUtils {

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

    public static <T> boolean all(final Collection<T> collection, final Predicate<? super T> predicate) {
        if (collection.isEmpty()) return true;
        if (collection instanceof List && collection instanceof RandomAccess) return all((List<T>) collection, predicate);
        return allIterated(collection, predicate);
    }

    public static <T> boolean all(final List<T> list, final Predicate<? super T> predicate) {
        if (!(list instanceof RandomAccess)) return all((Collection<T>) list, predicate);
        for (int i = 0; i < list.size(); i++) {
            if (!predicate.test(list.get(i))) return false;
        }
        return true;
    }

    private static <T> boolean allIterated(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        for (final var element : iterable) {
            if (!predicate.test(element)) return false;
        }
        return true;
    }

    public static <T> Optional<T> findFirst(final Iterable<? extends T> iterable, final Predicate<? super T> predicate) {
        for (final var element : iterable) {
            if (predicate.test(element)) return Optional.of(element);
        }
        return Optional.empty();
    }

    public static <T> Stream<T> stream(final Iterable<T> iterable) {
        return Streams.stream(iterable);
    }

    public static <T extends Castable<? super T>> boolean has(final Iterable<? extends T> iterable, @Nonnull final Class<? extends T> type) {
        return IterableUtils.any(iterable, event -> event.is(type));
    }

    public static <T extends Castable<? super T>, R extends T> Set<R> findAll(@Nonnull final Iterable<? extends T> iterable, @Nonnull final Class<R> type) {
        return stream(iterable)
                .map(e -> e.as(type).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static int integerSum(final Iterable<? extends Number> iterable) {
        if (iterable instanceof Collection && ((Collection<?>) iterable).isEmpty()) return 0;
        int sum = 0;
        for (final var n : iterable) {
            sum += n.intValue();
        }
        return sum;
    }

}
