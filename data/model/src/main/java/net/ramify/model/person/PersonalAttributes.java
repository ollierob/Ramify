package net.ramify.model.person;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Birth;
import net.ramify.model.event.PersonalEvents;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;

/**
 * Attributes of a {@link Person person} on a {@link DateRange date}.
 */
public interface PersonalAttributes extends HasPerson {

    @Nonnull
    Person person();

    @Nonnull
    DateRange date();

    @Nonnull
    Name name();

    @Nonnull
    AgeRange age();

    @Nonnull
    Gender gender();

    @Nonnull
    default PersonalEvents inferredEvents() {
        return PersonalEvents.of(Birth.from(this.age(), this.date()));
    }

}
