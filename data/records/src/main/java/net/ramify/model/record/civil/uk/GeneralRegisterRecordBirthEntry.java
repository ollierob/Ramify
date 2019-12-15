package net.ramify.model.record.civil.uk;

import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.EventId;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.record.GenericRecordEntry;

public class GeneralRegisterRecordBirthEntry extends GenericRecordEntry {

    private final EventId birthEventId = EventId.random(); //FIXME inject

    public GeneralRegisterRecordBirthEntry(
            final PersonId id,
            final Name name,
            final Sex gender) {
        super(id, name, gender, null, null, Age.ZERO, false);
    }

    Person build(final GeneralRegisterBirth record) {
        return super.build(this.events(record));
    }

    PersonEventSet events(final GeneralRegisterBirth record) {
        return new MutablePersonEventSet(this.birth(record));
    }

    private BirthEvent birth(final GeneralRegisterBirth record) {
        return EventBuilder.builder(birthEventId, record.birthDate())
                .withPlace(record.birthPlace())
                .toBirth(this.personId());
    }

}
