package net.ramify.model.event.type.misc;

import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class Flourished extends AbstractPersonEvent<Flourished> implements LifeEvent {

    public Flourished(final EventId id, final PersonId personId, final EventProperties properties) {
        super(id, personId, properties);
    }

    @Nonnull
    @Override
    public Flourished with(final Place place) {
        return this;
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return super.toProtoBuilder().setType(EventProto.RecordType.MENTION);
    }

    @Nonnull
    @Override
    public String title() {
        return "Flourished";
    }

}
