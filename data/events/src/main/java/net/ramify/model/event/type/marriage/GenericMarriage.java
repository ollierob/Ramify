package net.ramify.model.event.type.marriage;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;

import javax.annotation.Nonnull;
import java.util.Optional;

public class GenericMarriage extends AbstractEvent<GenericMarriage> implements LifeEvent, MarriageEvent {

    private final Age givenAge;

    public GenericMarriage(
            final PersonId personId,
            final DateRange date) {
        this(personId, date, null);
    }

    public GenericMarriage(
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
    public String title() {
        return "Marriage";
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return MarriageEvent.super.toProtoBuilder();
    }

}
