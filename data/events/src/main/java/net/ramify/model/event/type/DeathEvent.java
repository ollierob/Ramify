package net.ramify.model.event.type;

import net.ramify.model.event.proto.EventProto;

import javax.annotation.Nonnull;

public interface DeathEvent extends UniqueEvent {

    @Override
    default <R> R handleWith(final EventHandler<R> handler) {
        return handler.handle(this);
    }

    @Nonnull
    @Override
    default String title() {
        return "Death";
    }

    @Nonnull
    @Override
    default Class<? extends UniqueEvent> uniqueType() {
        return DeathEvent.class;
    }

    @Nonnull
    @Override
    default EventProto.Event.Builder toProtoBuilder() {
        return UniqueEvent.super.toProtoBuilder().setType(EventProto.RecordType.DEATH);
    }

}
