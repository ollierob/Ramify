package net.ramify.strategy.merge;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Merger<F, T> {

    @Nonnull
    Result<T> merge(F f1, F f2);

    default Result<T> merge(final Optional<? extends F> f1, final Optional<? extends F> f2) {
        if (f1.isEmpty() && f2.isEmpty()) return unknown();
        if (f1.isEmpty()) return value(this.just(f2.get()));
        if (f2.isEmpty()) return value(this.just(f1.get()));
        return this.merge(f1.get(), f2.get());
    }

    T just(F from);

    interface Result<T> {

        @Nonnull
        Optional<T> value();

        default boolean isUnknown() {
            return this.value().isEmpty() && !this.isImpossible();
        }

        default boolean isImpossible() {
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
            public boolean isImpossible() {
                return true;
            }
        };
    }

}
