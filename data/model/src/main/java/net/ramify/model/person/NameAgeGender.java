package net.ramify.model.person;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Birth;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.address.Address;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class NameAgeGender implements PersonalAttributes {

    private final Person person;
    private final AgeRange age;
    private final Name name;
    private final Gender gender;
    private final DateRange date;

    public NameAgeGender(
            final Person person,
            final Name name,
            final Gender gender,
            final AgeRange age,
            final DateRange date) {
        this.person = person;
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.date = date;
    }

    @Nonnull
    @Override
    public Person person() {
        return person;
    }

    @Nonnull
    @Override
    public Name name() {
        return name;
    }

    @Nonnull
    @Override
    public Gender gender() {
        return gender;
    }

    @Override
    public AgeRange age() {
        return age;
    }

    @Override
    public DateRange date() {
        return date;
    }

    @CheckForNull
    protected Birth birth(final DateRange date) {
        return age == null
                ? null
                : new Birth(date.minus(age.max(), age.min()), "Inferred birth of " + person, this.birthplace());
    }

    protected Address birthplace() {
        return Address.UNKNOWN;
    }

}
