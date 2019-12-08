package net.ramify.model.event.type.burial;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.place.Place;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Optional;

public class ChurchBurial extends AbstractEvent<ChurchBurial> implements Burial {

    private final Age givenAge;
    private final Place burialPlace;

    public ChurchBurial(final PersonId personId, final DateRange date, @CheckForNull final Age givenAge, final Place burialPlace) {
        super(personId, date);
        this.givenAge = givenAge;
        this.burialPlace = burialPlace;
    }

    @Nonnull
    @Override
    public Place place() {
        return burialPlace;
    }

    @Nonnull
    @Override
    public Optional<Age> givenAge() {
        return Optional.ofNullable(givenAge);
    }

}
