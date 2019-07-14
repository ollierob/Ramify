package net.ramify.model.event.type;

import net.ramify.model.event.Event;
import net.ramify.model.event.proto.EventProto;

import javax.annotation.Nonnull;

public interface LifeEvent extends Event {

    @Override
    default <R> R handleWith(final EventHandler<R> handler) {
        return handler.handle(this);
    }

    @Nonnull
    @Override
    default EventProto.Event.Builder toProtoBuilder() {
        return Event.super.toProtoBuilder();
    }
}
