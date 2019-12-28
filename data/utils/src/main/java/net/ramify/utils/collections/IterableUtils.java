package net.ramify.utils.collections;

import com.google.common.collect.Streams;
import net.meerkat.objects.Castable;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.meerkat.collections.Iterables.any;

public class IterableUtils {

    public static <T> Stream<T> stream(final Iterable<T> iterable) {
        return Streams.stream(iterable);
    }

    public static <T extends Castable<? super T>> boolean has(final Iterable<? extends T> iterable, @Nonnull final Class<? extends T> type) {
        return any(iterable, event -> event.is(type));
    }

    public static <T extends Castable<? super T>, R extends T> Optional<R> findFirst(@Nonnull final Iterable<? extends T> iterable, @Nonnull final Class<R> type) {
        return stream(iterable)
                .map(e -> e.as(type))
                .filter(Optional::isPresent)
                .findAny()
                .orElseGet(Optional::empty);
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
