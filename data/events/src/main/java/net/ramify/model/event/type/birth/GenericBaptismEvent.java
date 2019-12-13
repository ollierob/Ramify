package net.ramify.model.event.type.birth;

import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class GenericBaptismEvent extends AbstractPersonEvent<GenericBaptismEvent> implements BaptismEvent {

    public GenericBaptismEvent(final EventId eventId, final PersonId personId, final EventProperties properties) {
        super(eventId, personId, properties);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return BaptismEvent.super.toProtoBuilder();
    }

    @Nonnull
    @Override
    public BaptismEvent with(final Place place) {
        return place == null ? this : new BaptismWithPlace(this, place);
    }

}
