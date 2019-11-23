package net.ramify.strategy.merge;

import net.ramify.utils.objects.OptionalBoolean;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Merger<F, T> {

    @Nonnull
    Result<T> merge(F f1, F f2);

    default Result<T> merge(final Optional<? extends F> f1, final Optional<? extends F> f2) {
        if (f1.isEmpty() && f2.isEmpty()) return Result.unknown();
        if (f1.isEmpty()) return Result.of(this.just(f2.get()));
        if (f2.isEmpty()) return Result.of(this.just(f1.get()));
        return this.merge(f1.get(), f2.get());
    }

    T just(F from);

    interface Result<T> {

        @Nonnull
        Optional<T> value();

        default T require() {
            return this.value().get();
        }

        default boolean isPossible() {
            return this.value().isPresent();
        }

        default boolean isUnknown() {
            return this.value().isEmpty() && !this.isImpossible();
        }

        default boolean isImpossible() {
            return false;
        }

        default OptionalBoolean isPresent() {
            if (this.isImpossible()) return OptionalBoolean.empty();
            return OptionalBoolean.of(this.value().isPresent());
        }

        default <R> Result<R> notPossible() {
            return this.isImpossible() ? impossible() : unknown();
        }

        static <T> Result<T> of(@Nonnull final T value) {
            return () -> Optional.of(value);
        }

        static <T> Result<T> unknown() {
            return Optional::empty;
        }

        static <T> Result<T> impossible() {
            return new Result<>() {

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

}
