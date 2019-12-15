package net.ramify.model.event.type.death;

import net.meerkat.functions.consumers.Consumers;
import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class GenericDeathEvent extends AbstractPersonEvent<GenericDeathEvent> implements DeathEvent {

    private final String cause;

    public GenericDeathEvent(
            final EventId id,
            final PersonId personId,
            final EventProperties properties,
            final String cause) {
        super(id, personId, properties);
        this.cause = cause;
    }

    @Override
    public DeathEvent with(final Place place) {
        return place == null ? this : new DeathWithPlace(this, place);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        final var builder = DeathEvent.super.toProtoBuilder();
        Consumers.ifNonNull(cause, builder::setCause);
        return builder;
    }

}
