package net.ramify.model.record.civil.uk;

import net.ramify.model.event.type.civil.GenericBirth;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;

public class GeneralRegisterRecordChild {

    private final PersonId id;
    private final Name name;
    private final Sex gender;

    public GeneralRegisterRecordChild(
            final PersonId id,
            final Name name,
            final Sex gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    Person buildWithBirth(final GeneralRegisterBirth birth) {
        return new GeneralRegisterRecordPerson(id, name, gender, new GenericBirth(id, birth.birthDate()).with(birth.birthPlace()));
    }

}
