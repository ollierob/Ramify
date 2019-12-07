package net.ramify.utils.collections;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MultimapCollectors {

    public static <F, K, V> Collector<F, ?, Multimap<K, V>> collector(final Function<? super F, ? extends K> toKey, final Function<? super F, ? extends V> toValue) {
        return collector(toKey, toValue, HashMultimap::create);
    }

    public static <F, K, V, M extends Multimap<K, V>> Collector<F, ?, M> collector(
            final Function<? super F, ? extends K> toKey,
            final Function<? super F, ? extends V> toValue,
            final Supplier<? extends M> createMultimap) {

        return new Collector<F, M, M>() {

            @Override
            public Supplier<M> supplier() {
                return createMultimap::get;
            }

            @Override
            public BiConsumer<M, F> accumulator() {
                return (map, from) -> map.put(toKey.apply(from), toValue.apply(from));
            }

            @Override
            public BinaryOperator<M> combiner() {
                return MultimapCollectors::mergeIntoLeft;
            }

            @Override
            public Function<M, M> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return ImmutableSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
            }

        };

    }

    private static <K, V, M extends Multimap<K, V>> M mergeIntoLeft(final M left, final M right) {
        left.putAll(right);
        return left;
    }

}
