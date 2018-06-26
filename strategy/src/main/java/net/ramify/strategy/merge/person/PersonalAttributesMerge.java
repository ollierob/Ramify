package net.ramify.strategy.merge.person;

import net.ramify.model.person.PersonalAttributes;
import net.ramify.strategy.merge.Merge;
import net.ramify.strategy.merge.person.gender.GenderMerge;
import net.ramify.strategy.merge.person.name.NameMerge;

public interface PersonalAttributesMerge extends Merge<PersonalAttributes> {

    PersonalAttributesMerge DEFAULT = new SameNameAndGender(NameMerge.DEFAULT, GenderMerge.DEFAULT);

}
