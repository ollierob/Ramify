package net.ramify.model.record.civil.uk;

import com.google.common.collect.Sets;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.civil.GenericBirth;
import net.ramify.model.event.type.civil.GenericDeath;
import net.ramify.model.event.type.misc.Flourished;
import net.ramify.model.event.type.residence.GenericResidence;
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

public class GeneralRegisterRecordEntry {

    final PersonId id;
    final Name name;
    final Sex gender;
    final Occupation occupation;
    final PlaceId residence;
    final Age age;
    final boolean predeceased;

    public GeneralRegisterRecordEntry(
            @Nonnull final PersonId id,
            @Nonnull final Name name,
            @Nonnull final Sex gender,
            @CheckForNull final PlaceId residence,
            @CheckForNull final Occupation occupation,
            @CheckForNull final Age age,
            final boolean predeceased) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.occupation = occupation;
        this.residence = residence;
        this.age = age;
        this.predeceased = predeceased;
    }

    @CheckForNull
    PlaceId residence() {
        return residence;
    }

    @Nonnull
    Person build(final GeneralRegisterRecord record) {
        return this.build(this.events(record));
    }

    @Nonnull
    Person build(final Set<Event> events) {
        return new GeneralRegisterRecordPerson(id, name, gender, events);
    }

    @Nonnull
    Set<Event> events(final GeneralRegisterRecord record) {
        final var events = Sets.<Event>newHashSet();
        if (age != null) events.add(new GenericBirth(id, age.birthDate(record.date())));
        if (residence != null) events.add(new GenericResidence(id, record.date(), residence));
        if (predeceased) events.add(new GenericDeath(id, DateRange.before(record.date())));
        if (events.isEmpty()) events.add(new Flourished(id, record.date()));
        return events;
    }

}
