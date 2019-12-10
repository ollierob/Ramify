package net.ramify.model.event.type.marriage;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class GenericMarriage implements LifeEvent, MarriageEvent {

    private final DateRange date;
    private final Age givenAge;
    private final PersonId firstSpouse;
    private final PersonId secondSpouse;

    public GenericMarriage(
            final PersonId firstSpouse,
            final PersonId secondSpouse,
            final DateRange date) {
        this(firstSpouse, secondSpouse, date, null);
    }

    public GenericMarriage(
            final PersonId firstSpouse,
            final PersonId secondSpouse,
            final DateRange date,
            final Age givenAge) {
        this.firstSpouse = firstSpouse;
        this.secondSpouse = secondSpouse;
        this.date = date;
        this.givenAge = givenAge;
    }

    @Nonnull
    @Override
    public Optional<Age> givenAge() {
        return Optional.ofNullable(givenAge);
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @Nonnull
    @Override
    public Set<PersonId> personIds() {
        return ImmutableSet.of(firstSpouse, secondSpouse);
    }

    public MarriageEvent with(final Place place) {
        return place == null ? this : new GenericMarriageWithPlace(firstSpouse, secondSpouse, date, givenAge, place);
    }

    @Nonnull
    @Override
    public String title() {
        return "Marriage";
    }

}
