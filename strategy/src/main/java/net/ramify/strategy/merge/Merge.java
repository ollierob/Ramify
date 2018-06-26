package net.ramify.strategy.merge;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @param <T>
 * @see net.ramify.strategy.join.Join
 */
public interface Merge<T> {

    @Nonnull
    Optional<T> merge(@Nonnull T t1, @Nonnull T t2);

    default boolean canMerge(final T t1, final T t2) {
        return this.merge(t1, t2).isPresent();
    }

}
