package net.ramify.model.record.civil.uk;

import net.ramify.model.event.Event;
import net.ramify.model.event.type.civil.GenericBirth;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;

import java.util.Collections;
import java.util.Set;

public class GeneralRegisterRecordBirthEntry extends GeneralRegisterRecordEntry {

    public GeneralRegisterRecordBirthEntry(
            final PersonId id,
            final Name name,
            final Sex gender) {
        super(id, name, gender, null, null, Age.ZERO, false);
    }

    Person build(final GeneralRegisterBirth record) {
        return super.build(this.events(record));
    }

    Set<Event> events(final GeneralRegisterBirth record) {
        return Collections.singleton(new GenericBirth(id, record.birthDate()).with(record.birthPlace()));
    }

}
