package net.ramify.model.person.features;

import net.ramify.utils.collections.IterableUtils;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public interface HasPersonFeatures {

    @Nonnull
    Set<? extends PersonFeature> features();

    default <F extends PersonFeature> Optional<F> findFeature(final Class<F> type) {
        return IterableUtils.findFirst(this.features(), type);
    }

}
