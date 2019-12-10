package net.ramify.model.event.type.misc;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.Event;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;

public class WitnessedEvent<T extends Event> extends AbstractPersonEvent<WitnessedEvent<T>> implements LifeEvent {

    private final T observed;

    public WitnessedEvent(final PersonId personId, final DateRange date, final T observed) {
        super(personId, date);
        this.observed = observed;
    }

    @Nonnull
    @Override
    public String title() {
        return "Witness (" + observed.title() + ")";
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return LifeEvent.super.toProtoBuilder().setType(EventProto.RecordType.MENTION);
    }

}
