package net.ramify.model.strategy;

import com.google.common.base.Preconditions;
import net.meerkat.functions.optionals.OptionalBoolean;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Consumer;

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

        @Nonnull
        default T require() {
            return this.value().get();
        }

        default T orElse(final T value) {
            return this.value().orElse(value);
        }

        @CheckForNull
        default T orNull() {
            return this.value().orElse(null);
        }

        default boolean isMergeable() {
            return this.value().isPresent();
        }

        default boolean isUnknownMerge() {
            return this.value().isEmpty() && !this.isImpossibleMerge();
        }

        default boolean isImpossibleMerge() {
            return false;
        }

        default OptionalBoolean isPresent() {
            if (this.isImpossibleMerge()) return OptionalBoolean.empty();
            return OptionalBoolean.of(this.value().isPresent());
        }

        default <R> Result<R> cannotMerge() {
            return this.isImpossibleMerge() ? impossible() : unknown();
        }

        default void ifPresent(final Consumer<? super T> consumer) {
            Preconditions.checkState(!this.isImpossibleMerge());
            this.value().ifPresent(consumer);
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
                public boolean isImpossibleMerge() {
                    return true;
                }

            };
        }

    }

}
