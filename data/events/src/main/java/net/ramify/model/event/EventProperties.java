package net.ramify.model.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.HasDate;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.age.HasAges;

import javax.annotation.Nonnull;
import java.util.Optional;

public class EventProperties implements HasDate, HasAges {

    private final DateRange date;
    private final Age givenAge;
    private final String occupation;
    private final boolean inferred;
    private final String description;

    EventProperties(final DateRange date, final Age givenAge, final String occupation, final boolean inferred, final String description) {
        this.date = date;
        this.givenAge = givenAge;
        this.occupation = occupation;
        this.inferred = inferred;
        this.description = description;
    }

    @Override
    public DateRange date() {
        return date;
    }

    @Override
    public Optional<Age> givenAge() {
        return Optional.ofNullable(givenAge);
    }

    @Nonnull
    public Optional<String> occupation() {
        return Optional.ofNullable(occupation);
    }

    @Nonnull
    public Optional<String> description() {
        return Optional.ofNullable(description);
    }

    public boolean isInferred() {
        return inferred;
    }

}
