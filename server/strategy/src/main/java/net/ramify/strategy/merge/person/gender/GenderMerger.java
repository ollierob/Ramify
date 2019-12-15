package net.ramify.strategy.merge.person.gender;

import net.ramify.model.person.gender.Gender;
import net.ramify.model.strategy.Merger;

public interface GenderMerger extends Merger<Gender, Gender> {

    @Override
    default Gender just(final Gender gender) {
        return gender;
    }

}
