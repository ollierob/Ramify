package net.ramify.model.place;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.DateRange;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface PlaceHistory extends BuildsProto<PlaceProto.PlaceHistory> {

    @CheckForNull
    DateRange opened();

    @CheckForNull
    DateRange closed();

    default boolean hasClosure() {
        return this.closed() != null;
    }

    @Nonnull
    default PlaceProto.PlaceHistory.Builder toProtoBuilder() {
        final var builder = PlaceProto.PlaceHistory.newBuilder();
        Consumers.ifNonNull(this.opened(), d -> builder.setOpen(d.toProto()));
        Consumers.ifNonNull(this.closed(), d -> builder.setClose(d.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default PlaceProto.PlaceHistory toProto() {
        return this.toProtoBuilder().build();
    }

}
