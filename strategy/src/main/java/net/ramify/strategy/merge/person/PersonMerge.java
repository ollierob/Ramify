package net.ramify.strategy.merge.person;

import net.ramify.model.person.Person;
import net.ramify.strategy.merge.Merge;
import net.ramify.strategy.merge.person.gender.GenderMerge;
import net.ramify.strategy.merge.person.name.NameMerge;

public interface PersonMerge extends Merge<Person> {

    PersonMerge DEFAULT = new SameNameAndGender(NameMerge.DEFAULT, GenderMerge.DEFAULT);

}
