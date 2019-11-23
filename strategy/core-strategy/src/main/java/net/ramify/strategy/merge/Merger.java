package net.ramify.strategy.merge;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Merger<F, T> {

    @Nonnull
    Result<T> merge(F f1, F f2);

    interface Result<T> {

        @Nonnull
        Optional<T> value();

        default boolean unknown() {
            return this.value().isEmpty();
        }

        default boolean impossible() {
            return false;
        }

    }

    static <T> Merger.Result<T> value(final T value) {
        return () -> Optional.of(value);
    }

    static <T> Merger.Result<T> unknown() {
        return Optional::empty;
    }

    static <T> Merger.Result<T> impossible() {
        return new Result<T>() {

            @Nonnull
            @Override
            public Optional<T> value() {
                return Optional.empty();
            }

            @Override
            public boolean impossible() {
                return true;
            }
        };
    }

}
