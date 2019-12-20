package net.ramify.model.event.historic;

import com.google.common.base.MoreObjects;
import net.ramify.model.event.Event;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.type.SettlementOrRegion;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface HistoricEvent extends Event, HasPlace {

    @CheckForNull
    String description();

    @Nonnull
    SettlementOrRegion region();

    @Nonnull
    default EventProto.Event.Builder toProtoBuilder() {
        return Event.super.toProtoBuilder()
                .setType(EventProto.RecordType.HISTORIC)
                .setDescription(MoreObjects.firstNonNull(this.description(), ""));
    }

}
