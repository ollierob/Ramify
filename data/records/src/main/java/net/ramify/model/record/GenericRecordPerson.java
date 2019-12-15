package net.ramify.model.record;

import com.google.common.base.MoreObjects;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;

public class GenericRecordPerson extends AbstractPerson {

    private final PersonEventSet events;
    private final String notes;

    public GenericRecordPerson(final PersonId id, final Name name, final Gender gender, final PersonEventSet events, final String notes) {
        super(id, name, gender);
        this.events = events;
        this.notes = notes;
    }

    @Nonnull
    @Override
    public PersonEventSet events() {
        return events;
    }

    @Nonnull
    @Override
    public PersonProto.Person.Builder toProtoBuilder() {
        return super.toProtoBuilder()
                .setNotes(MoreObjects.firstNonNull(notes, ""));
    }

}

