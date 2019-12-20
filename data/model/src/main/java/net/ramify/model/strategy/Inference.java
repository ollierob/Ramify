package net.ramify.model.strategy;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Inference<F, T> {

    @Nonnull
    Optional<T> infer(F from);

}
