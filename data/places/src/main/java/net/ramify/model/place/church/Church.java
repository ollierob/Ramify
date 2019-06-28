package net.ramify.model.place.church;

import com.google.common.base.MoreObjects;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.DateRange;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.proto.ChurchProto;
import net.ramify.model.place.type.Building;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface Church extends HasPlace, BuildsProto<ChurchProto.Church> {

    @Nonnull
    Building place();

    @CheckForNull
    String denomination();

    @Nonnull
    DateRange founded();

    @CheckForNull
    DateRange closed();

    default boolean isRedundant() {
        return this.closed() != null;
    }

    @Nonnull
    default ChurchProto.Church.Builder toProtoBuilder() {
        final var builder = ChurchProto.Church.newBuilder()
                .setPlace(this.place().toProto())
                .setDenomination(MoreObjects.firstNonNull(this.denomination(), ""))
                .setEstablished(this.founded().toProto());
        Consumers.ifNonNull(this.closed(), c -> builder.setClosed(c.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default ChurchProto.Church toProto() {
        return this.toProtoBuilder().build();
    }

}
