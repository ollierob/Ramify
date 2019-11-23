package net.ramify.strategy.person.merge.gender;

import net.ramify.model.person.gender.Gender;
import net.ramify.strategy.merge.Merger;

public interface GenderMerger extends Merger<Gender, Gender> {

    @Override
    default Gender just(final Gender gender) {
        return gender;
    }

}
