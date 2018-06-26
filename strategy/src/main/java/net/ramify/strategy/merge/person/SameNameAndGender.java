package net.ramify.strategy.merge.person;

import net.ramify.model.person.PersonalAttributes;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.strategy.merge.person.gender.GenderMerge;
import net.ramify.strategy.merge.person.name.NameMerge;

import javax.annotation.Nonnull;
import java.util.Optional;

class SameNameAndGender implements PersonalAttributesMerge {

    private final NameMerge nameMerge;
    private final GenderMerge genderMerge;

    SameNameAndGender(NameMerge nameMerge, GenderMerge genderMerge) {
        this.nameMerge = nameMerge;
        this.genderMerge = genderMerge;
    }

    @Nonnull
    @Override
    public Optional<PersonalAttributes> merge(@Nonnull final PersonalAttributes p1, @Nonnull final PersonalAttributes p2) {
        final Optional<Gender> gender = genderMerge.merge(p1.gender(), p2.gender());
        if (!gender.isPresent()) {
            return Optional.empty();
        }
        final Optional<Name> name = nameMerge.merge(p1.name(), p2.name());
        if (!name.isPresent()) {
            return Optional.empty();
        }
        //return Optional.of(Person.named(name.get(), gender.get()));
        throw new UnsupportedOperationException(); //TODO
    }

}
