package net.ramify.model.event.historic;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import net.ramify.model.event.Event;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.type.SettlementOrRegion;
import net.ramify.model.util.Link;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

public interface HistoricEvent extends Event, HasPlace {

    @CheckForNull
    String description();

    @Nonnull
    SettlementOrRegion region();

    @Nonnull
    Set<Link> links();

    @Nonnull
    default EventProto.Event.Builder toProtoBuilder() {
        return Event.super.toProtoBuilder()
                .setType(EventProto.RecordType.HISTORIC)
                .setDescription(MoreObjects.firstNonNull(this.description(), ""))
                .addAllLink(Iterables.transform(this.links(), Link::toProto));
    }

}
