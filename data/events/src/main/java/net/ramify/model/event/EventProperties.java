package net.ramify.model.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.HasDate;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.age.HasAges;

import java.util.Optional;

public class EventProperties implements HasDate, HasAges {

    private final DateRange date;
    private final Age givenAge;

    EventProperties(final DateRange date, final Age givenAge) {
        this.date = date;
        this.givenAge = givenAge;
    }

    @Override
    public DateRange date() {
        return date;
    }

    @Override
    public Optional<Age> givenAge() {
        return Optional.ofNullable(givenAge);
    }

}
