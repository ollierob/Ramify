package net.ramify.strategy.merge.family;

import net.ramify.model.family.Family;
import net.ramify.strategy.merge.Merger;

public interface FamilyMerger extends Merger<Family, Family> {

    @Override
    default Family just(final Family family) {
        return family;
    }

}
