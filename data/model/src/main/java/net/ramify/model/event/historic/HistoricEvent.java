package net.ramify.model.event.historic;

import com.google.common.collect.Iterables;
import net.meerkat.functions.consumers.Consumers;
import net.ramify.model.event.Event;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.type.SettlementOrRegion;
import net.ramify.model.util.Link;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

public interface HistoricEvent extends Event {

    @CheckForNull
    SettlementOrRegion region();

    default boolean isGlobal() {
        return this.region() == null;
    }

    @Nonnull
    Set<Link> links();

    @Nonnull
    default EventProto.Event.Builder toProtoBuilder() {
        final var builder = Event.super.toProtoBuilder()
                .setType(EventProto.RecordType.HISTORIC)
                .addAllLink(Iterables.transform(this.links(), Link::toProto));
        Consumers.ifNonNull(this.region(), region -> builder.setPlace(region.toProto()));
        return builder;
    }

}
