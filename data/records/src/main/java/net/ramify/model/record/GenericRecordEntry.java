package net.ramify.model.record;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.Place;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class GenericRecordEntry implements HasPersonId {

    private final PersonId personId;
    private final Name name;
    private final Sex gender;
    private final String occupation;
    private final Place residence;
    private final Age age;
    final boolean predeceased;

    public GenericRecordEntry(
            @Nonnull final PersonId personId,
            @Nonnull final Name name,
            @Nonnull final Sex gender,
            @CheckForNull final Place residence,
            @CheckForNull final String occupation,
            @CheckForNull final Age age,
            final boolean predeceased) {
        this.personId = personId;
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
        return personId;
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
    public Person build(final PersonEventSet events) {
        return new GenericRecordPerson(personId, name, gender, events, null);
    }

    @Nonnull
    public PersonEventSet events(final Record record) {
        final var events = MutablePersonEventSet.notPermittingMerge();
        if (age != null) events.add(this.eventBuilder(age.birthDate(record.date())).toBirth(personId));
        if (residence != null) events.add(this.eventBuilder(record.date()).withPlace(residence).toResidence(personId));
        if (predeceased) events.add(this.eventBuilder(BeforeDate.strictlyBefore(record.date())).toDeath(personId));
        if (events.isEmpty()) events.add(this.eventBuilder(record.date()).toFlourished(personId));
        return events;
    }

    protected EventBuilder eventBuilder(final DateRange date) {
        return EventBuilder.builderWithRandomId(date);
    }

}
