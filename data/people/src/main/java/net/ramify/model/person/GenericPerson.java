package net.ramify.model.person;

import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public class GenericPerson extends AbstractPerson {

    private final PersonEventSet events;
    private final Set<? extends PersonFeature> features;

    public GenericPerson(
            final PersonId id,
            final Name name,
            final Gender gender,
            final PersonEventSet events) {
        super(id, name, gender);
        this.events = events;
        this.features = Collections.emptySet();
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
