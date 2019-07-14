package net.ramify.model.event.type.residence;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.place.HasPlaceId;

import javax.annotation.Nonnull;

public interface Residence extends LifeEvent, HasPlaceId {

    @Nonnull
    @Override
    default EventProto.Event.Builder toProtoBuilder() {
        return LifeEvent.super.toProtoBuilder().setType(EventProto.RecordType.RESIDENCE);
    }

}
