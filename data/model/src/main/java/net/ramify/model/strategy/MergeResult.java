package net.ramify.model.strategy;

import com.google.common.base.Preconditions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Consumer;

public interface MergeResult<T> {

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

    default <R> MergeResult<R> cannotMerge() {
        return this.isImpossibleMerge() ? impossible() : unknown();
    }

    default void ifPresent(final Consumer<? super T> consumer) {
        Preconditions.checkState(!this.isImpossibleMerge());
        this.value().ifPresent(consumer);
    }

    static <T> MergeResult<T> of(@Nonnull final T value) {
        return () -> Optional.of(value);
    }

    static <T> MergeResult<T> ofNullable(@CheckForNull final T value) {
        return () -> Optional.ofNullable(value);
    }

    static <T> MergeResult<T> unknown() {
        return Optional::empty;
    }

    static <T> MergeResult<T> impossible() {
        return new MergeResult<>() {

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
