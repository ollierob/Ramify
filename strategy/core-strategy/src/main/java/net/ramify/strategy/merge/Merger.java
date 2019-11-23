package net.ramify.strategy.merge;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Merger<F, T> {

    @Nonnull
    Optional<T> merge(F f1, F f2);

}
