package net.ramify.model.person;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Occupation;
import net.ramify.model.event.PersonalEvents;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;
import java.util.Optional;

public class NameAgeGenderOccupation extends NameAgeGender {

    private final String occupation;

    public NameAgeGenderOccupation(
            final PersonId person,
            final Name name,
            final Gender gender,
            final AgeRange age,
            final DateRange date,
            final String occupation) {
        super(person, name, gender, age, date);
        this.occupation = occupation;
    }

    @Nonnull
    public Optional<String> occupation() {
        return Optional.ofNullable(occupation);
    }

    @Nonnull
    @Override
    public PersonalEvents inferredEvents() {
        final PersonalEvents events = super.inferredEvents();
        return this.occupation()
                .map(occupation -> events.and(new Occupation(this.date(), occupation)))
                .orElse(events);
    }

}
