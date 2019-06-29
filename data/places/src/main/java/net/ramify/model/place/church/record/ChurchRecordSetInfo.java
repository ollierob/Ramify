package net.ramify.model.place.church.record;

import com.google.common.base.MoreObjects;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.HasDate;
import net.ramify.model.place.proto.ChurchProto;
import net.ramify.model.record.set.HasRecordSetId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface ChurchRecordSetInfo extends HasRecordSetId, HasDate, BuildsProto<ChurchProto.ChurchRecordSet> {

    @Nonnull
    String name();

    @CheckForNull
    String notes();

    @Nonnull
    default ChurchProto.ChurchRecordSet.Builder toProtoBuilder() {
        return ChurchProto.ChurchRecordSet.newBuilder()
                .setId(this.recordSetId().value())
                .setName(this.name())
                .setCovers(this.date().toProto())
                .setNotes(MoreObjects.firstNonNull(this.notes(), ""));
    }

    @Nonnull
    @Override
    default ChurchProto.ChurchRecordSet toProto() {
        return this.toProtoBuilder().build();
    }

}
