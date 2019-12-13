package net.ramify.model.event.type.burial;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.EventHandler;
import net.ramify.model.event.type.PostDeathEvent;
import net.ramify.model.event.type.UniqueEvent;
import net.ramify.model.place.HasPlace;

import javax.annotation.Nonnull;

public interface BurialEvent extends UniqueEvent, PostDeathEvent, HasPlace {

    @Override
    default <R> R handleWith(final EventHandler<R> handler) {
        return handler.handle(this);
    }

    @Nonnull
    @Override
    default String title() {
        return "Burial";
    }

    @Nonnull
    @Override
    default EventProto.Event.Builder toProtoBuilder() {
        return UniqueEvent.super.toProtoBuilder()
                .setType(EventProto.RecordType.BURIAL);
    }

}
