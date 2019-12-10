package net.ramify.model.record;

import com.google.common.collect.Sets;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.birth.GenericBirthEvent;
import net.ramify.model.event.type.death.GenericDeathEvent;
import net.ramify.model.event.type.misc.Flourished;
import net.ramify.model.event.type.residence.GenericResidenceEvent;
import net.ramify.model.occupation.Occupation;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.Place;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

public class GenericRecordEntry implements HasPersonId {

    private final PersonId id;
    private final Name name;
    private final Sex gender;
    private final Occupation occupation;
    private final Place residence;
    private final Age age;
    final boolean predeceased;

    public GenericRecordEntry(
            @Nonnull final PersonId id,
            @Nonnull final Name name,
            @Nonnull final Sex gender,
            @CheckForNull final Place residence,
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

    @Nonnull
    @Override
    public PersonId personId() {
        return id;
    }

    @CheckForNull
    public Place residence() {
        return residence;
    }

    @Nonnull
    public Person build(final Record record) {
        return this.build(this.events(record));
    }

    @Nonnull
    public Person build(final Set<Event> events) {
        return new GenericRecordPerson(id, name, gender, events, null);
    }

    @Nonnull
    public Set<Event> events(final Record record) {
        final var events = Sets.<Event>newHashSet();
        if (age != null) events.add(new GenericBirthEvent(id, age.birthDate(record.date())));
        if (residence != null) events.add(new GenericResidenceEvent(id, record.date(), residence));
        if (predeceased) events.add(new GenericDeathEvent(id, BeforeDate.strictlyBefore(record.date())));
        if (events.isEmpty()) events.add(new Flourished(id, record.date()));
        return events;
    }

}
