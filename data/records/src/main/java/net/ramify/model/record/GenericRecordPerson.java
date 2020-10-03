package net.ramify.model.record;

import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public class GenericRecordPerson extends AbstractPerson {

    private final PersonEventSet events;
    private final Set<PersonFeature> features;

    public GenericRecordPerson(
            final PersonId id,
            final Name name,
            final Gender gender,
            final PersonEventSet events) {
        this(id, name, gender, events, Collections.emptySet());
    }

    public GenericRecordPerson(
            final PersonId id,
            final Name name,
            final Gender gender,
            final PersonEventSet events,
            final Set<PersonFeature> features) {
        super(id, name, gender);
        this.events = events;
        this.features = features;
    }

    @Nonnull
    @Override
    public PersonEventSet events() {
        return events;
    }

    @Nonnull
    @Override
    public Set<? extends PersonFeature> features() {
        return features;
    }

}

