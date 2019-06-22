package net.ramify.model.record.civil.uk;

import com.google.common.collect.Sets;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.civil.GenericBirth;
import net.ramify.model.event.type.civil.GenericDeath;
import net.ramify.model.occupation.Occupation;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.PlaceId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

public class GeneralRegisterRecordDeathEntry extends GeneralRegisterRecordEntry {

    private final Age age;

    public GeneralRegisterRecordDeathEntry(
            @Nonnull final PersonId id,
            @Nonnull final Name name,
            @Nonnull final Sex gender,
            @CheckForNull final PlaceId deathPlace,
            @CheckForNull final Occupation occupation,
            @CheckForNull final Age age) {
        super(id, name, gender, deathPlace, occupation, age, true);
        this.age = age;
    }

    @Nonnull
    Person build(final GeneralRegisterDeath record) {
        return this.build(this.events(record));
    }

    @Nonnull
    Set<Event> events(final GeneralRegisterDeath record) {
        final var events = Sets.<Event>newHashSet();
        events.add(new GenericDeath(id, record.deathDate()));
        if (age != null) events.add(new GenericBirth(id, age.birthDate(record.deathDate())));
        return events;
    }

}
