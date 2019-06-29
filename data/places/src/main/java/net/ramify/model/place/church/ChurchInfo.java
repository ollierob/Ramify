package net.ramify.model.place.church;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.DateRange;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.church.record.ChurchRecordSetInfo;
import net.ramify.model.place.proto.ChurchProto;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;

public interface ChurchInfo extends HasPlace, BuildsProto<ChurchProto.Church> {

    @Nonnull
    Church place();

    @CheckForNull
    String denomination();

    @Nonnull
    DateRange founded();

    @CheckForNull
    DateRange closed();

    @Nonnull
    Set<ChurchRecordSetInfo> records();

    default boolean isClosed() {
        return this.closed() != null;
    }

    @Nonnull
    default ChurchProto.Church.Builder toProtoBuilder() {
        final var builder = ChurchProto.Church.newBuilder()
                .setPlace(this.place().toProto())
                .setDenomination(MoreObjects.firstNonNull(this.denomination(), ""))
                .setEstablished(this.founded().toProto());
        Consumers.ifNonNull(this.closed(), c -> builder.setClosed(c.toProto()));
        this.records().forEach(record -> builder.addRecordSet(record.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default ChurchProto.Church toProto() {
        return this.toProtoBuilder().build();
    }

    @Nonnull
    static ChurchProto.ChurchList toProto(final Collection<ChurchInfo> churches) {
        final var list = ChurchProto.ChurchList.newBuilder();
        list.addAllChurch(Iterables.transform(churches, ChurchInfo::toProto));
        return list.build();
    }

}
