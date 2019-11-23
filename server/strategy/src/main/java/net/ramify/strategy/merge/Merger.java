package net.ramify.strategy.merge;

import com.google.common.base.Preconditions;
import net.ramify.utils.objects.OptionalBoolean;

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

        default boolean canMerge() {
            return this.value().isPresent();
        }

        default boolean unknownMerge() {
            return this.value().isEmpty() && !this.impossibleMerge();
        }

        default boolean impossibleMerge() {
            return false;
        }

        default OptionalBoolean isPresent() {
            if (this.impossibleMerge()) return OptionalBoolean.empty();
            return OptionalBoolean.of(this.value().isPresent());
        }

        default <R> Result<R> cannotMerge() {
            return this.impossibleMerge() ? impossible() : unknown();
        }

        default void ifPresent(final Consumer<? super T> consumer) {
            Preconditions.checkState(!this.impossibleMerge());
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
                public boolean impossibleMerge() {
                    return true;
                }

            };
        }

    }

}
