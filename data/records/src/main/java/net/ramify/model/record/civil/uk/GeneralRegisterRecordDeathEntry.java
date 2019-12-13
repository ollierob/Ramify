package net.ramify.model.record.civil.uk;

import com.google.common.collect.Sets;
import net.ramify.model.event.Event;
import net.ramify.model.occupation.Occupation;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.Place;
import net.ramify.model.record.GenericRecordEntry;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

public class GeneralRegisterRecordDeathEntry extends GenericRecordEntry {

    private final Age age;

    public GeneralRegisterRecordDeathEntry(
            @Nonnull final PersonId id,
            @Nonnull final Name name,
            @Nonnull final Sex gender,
            @CheckForNull final Place deathPlace,
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
        final var events = Sets.<Event>newHashSetWithExpectedSize(2);
        events.add(this.eventBuilder(record.deathDate()).withGivenAge(age).toDeath(this.personId()));
        if (age != null) events.add(this.eventBuilder(age.birthDate(record.deathDate())).toBirth(this.personId()));
        return events;
    }

}
