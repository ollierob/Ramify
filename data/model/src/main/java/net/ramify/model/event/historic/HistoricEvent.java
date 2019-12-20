package net.ramify.model.event.historic;

import net.ramify.model.event.Event;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.HasPlace;

import javax.annotation.Nonnull;

public interface HistoricEvent extends Event, HasPlace {

    @Nonnull
    default EventProto.Event.Builder toProtoBuilder() {
        return Event.super.toProtoBuilder()
                .setType(EventProto.RecordType.HISTORIC);
    }

}
