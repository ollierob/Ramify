package net.ramify.model.event;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.HasDate;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.age.HasAges;
import net.ramify.model.util.Link;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class EventProperties implements HasDate, HasAges {

    private final DateRange date;
    private final Age givenAge;
    private final String occupation;
    private final boolean inferred;

    EventProperties(final DateRange date, final Age givenAge, final String occupation, boolean inferred) {
        this.date = date;
        this.givenAge = givenAge;
        this.occupation = occupation;
        this.inferred = inferred;
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

    public boolean isInferred() {
        return inferred;
    }

}
