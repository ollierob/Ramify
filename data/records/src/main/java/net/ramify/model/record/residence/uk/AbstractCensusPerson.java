package net.ramify.model.record.residence.uk;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.residence.ResidenceEvent;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.Place;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public abstract class AbstractCensusPerson extends AbstractPerson {

    private final DateRange censusDate;
    private final String occupation;

    protected AbstractCensusPerson(
            final PersonId id,
            final Name name,
            final Gender gender,
            final DateRange censusDate, String occupation) {
        super(id, name, gender);
        this.censusDate = censusDate;
        this.occupation = occupation;
    }

    @CheckForNull
    protected BirthEvent birth(final Age age, final Place place) {
        if (age == null) return null;
        return this.birth(age.birthDate(censusDate), place);
    }

    protected BirthEvent birth(final DateRange date, final Place place) {
        return EventBuilder.builderWithRandomId(date)
                .withPlace(place)
                .toBirth(this);
    }

    @Nonnull
    protected ResidenceEvent residence(final Age age, final Place residencePlace) {
        return EventBuilder.builderWithRandomId(censusDate)
                .withGivenAge(age)
                .withPlace(residencePlace)
                .withOccupation(occupation)
                .toResidence(this);
    }

    protected Age age(final DateRange birthDate) {
        return Age.fromDates(birthDate, censusDate);
    }

}
