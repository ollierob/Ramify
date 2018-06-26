package net.ramify.strategy.merge.person.gender;

import net.ramify.model.person.gender.Gender;
import net.ramify.strategy.merge.Merge;

public interface GenderMerge extends Merge<Gender> {

    GenderMerge DEFAULT = new SameGender();

}
