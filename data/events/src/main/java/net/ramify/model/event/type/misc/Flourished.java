package net.ramify.model.event.type.misc;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;

public class Flourished extends AbstractPersonEvent<Flourished> implements LifeEvent {

    public Flourished(final PersonId personId, final DateRange date) {
        super(personId, date);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return LifeEvent.super.toProtoBuilder()
                .setType(EventProto.RecordType.MENTION);
    }

    @Nonnull
    @Override
    public String title() {
        return "Flourished";
    }

}
