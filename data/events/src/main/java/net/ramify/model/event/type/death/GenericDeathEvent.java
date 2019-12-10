package net.ramify.model.event.type.death;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Optional;

public class GenericDeathEvent extends AbstractPersonEvent<GenericDeathEvent> implements DeathEvent {

    private final Age givenAge;

    public GenericDeathEvent(
            final PersonId personId,
            final DateRange date) {
        this(personId, date, null);
    }

    public GenericDeathEvent(
            final PersonId personId,
            final DateRange date,
            final Age givenAge) {
        super(personId, date);
        this.givenAge = givenAge;
    }

    @Nonnull
    @Override
    public Optional<Age> givenAge() {
        return Optional.ofNullable(givenAge);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return DeathEvent.super.toProtoBuilder();
    }

    @Override
    public DeathEvent with(final Place place) {
        return place == null ? this : new DeathWithPlace(this, place);
    }

}
