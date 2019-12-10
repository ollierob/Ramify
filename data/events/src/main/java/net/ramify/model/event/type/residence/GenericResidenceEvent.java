package net.ramify.model.event.type.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public class GenericResidenceEvent extends AbstractPersonEvent<GenericResidenceEvent> implements ResidenceEvent {

    private final Place place;
    private final Age givenAge;

    public GenericResidenceEvent(
            @Nonnull final PersonId personId,
            @Nonnull final DateRange date,
            @Nonnull final Place place) {
        this(personId, date, place, null);
    }

    public GenericResidenceEvent(
            @Nonnull final PersonId personId,
            @Nonnull final DateRange date,
            @Nonnull final Place place,
            final Age givenAge) {
        super(personId, date);
        this.place = Objects.requireNonNull(place, "residence place");
        this.givenAge = givenAge;
    }

    @Nonnull
    @Override
    public Place place() {
        return place;
    }

    @Nonnull
    @Override
    public String title() {
        return "Residence";
    }

    @Nonnull
    @Override
    public Optional<Age> givenAge() {
        return Optional.ofNullable(givenAge);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return ResidenceEvent.super.toProtoBuilder();
    }

}
