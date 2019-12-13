package net.ramify.model.event.type.birth;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.LifeEvent;

import javax.annotation.Nonnull;

public interface BaptismEvent extends LifeEvent {

    @Nonnull
    @Override
    default String title() {
        return "Baptism";
    }

    @Nonnull
    @Override
    default EventProto.Event.Builder toProtoBuilder() {
        return LifeEvent.super.toProtoBuilder()
                .setType(EventProto.RecordType.BAPTISM);
    }

}
