package net.ramify.strategy.merge.person.feature;

import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.strategy.Merger;

public interface PersonFeatureMerger<F extends PersonFeature> extends Merger<F, F> {

    @Override
    default F just(final F from) {
        return from;
    }

}
