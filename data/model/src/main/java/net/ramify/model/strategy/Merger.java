package net.ramify.model.strategy;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Merger<F, T> {

    @Nonnull
    MergeResult<T> merge(F f1, F f2);

    default MergeResult<T> merge(final Optional<? extends F> f1, final Optional<? extends F> f2) {
        if (f1.isEmpty() && f2.isEmpty()) return MergeResult.unknown();
        if (f1.isEmpty()) return MergeResult.of(this.just(f2.get()));
        if (f2.isEmpty()) return MergeResult.of(this.just(f1.get()));
        return this.merge(f1.get(), f2.get());
    }

    T just(F from);

}
