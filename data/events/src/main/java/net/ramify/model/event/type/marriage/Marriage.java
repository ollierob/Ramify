package net.ramify.model.event.type.marriage;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.EventHandler;
import net.ramify.model.event.type.LifeEvent;

import javax.annotation.Nonnull;

public interface Marriage extends LifeEvent {

    @Override
    default <R> R handleWith(final EventHandler<R> handler) {
        return handler.handle(this);
    }

    @Nonnull
    @Override
    default String title() {
        return "Marriage";
    }

    @Nonnull
    @Override
    default EventProto.Event.Builder toProtoBuilder() {
        return LifeEvent.super.toProtoBuilder()
                .setType(EventProto.RecordType.MARRIAGE);
    }

}
