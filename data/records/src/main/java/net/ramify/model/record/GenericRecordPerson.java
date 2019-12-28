package net.ramify.model.record;

import com.google.common.base.MoreObjects;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public class GenericRecordPerson extends AbstractPerson {

    private final PersonEventSet events;
    private final String notes;
    private final Set<PersonFeature> features;

    @Deprecated
    public GenericRecordPerson(final PersonId id, final Name name, final Gender gender, final PersonEventSet events, final String notes) {
        this(id, name, gender, events, notes, Collections.emptySet());
    }

    public GenericRecordPerson(final PersonId id, final Name name, final Gender gender, final PersonEventSet events, final String notes, final Set<PersonFeature> features) {
        super(id, name, gender);
        this.events = events;
        this.notes = notes;
        this.features = features;
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

    @Nonnull
    @Override
    public Set<? extends PersonFeature> features() {
        return features;
    }

}

