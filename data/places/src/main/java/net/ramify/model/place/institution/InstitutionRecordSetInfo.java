package net.ramify.model.place.institution;

import com.google.common.base.MoreObjects;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.HasDate;
import net.ramify.model.place.proto.InstitutionProto;
import net.ramify.model.record.set.HasRecordSetId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface InstitutionRecordSetInfo extends HasRecordSetId, HasDate, BuildsProto<InstitutionProto.InstitutionRecordSet> {

    @Nonnull
    String name();

    @CheckForNull
    String notes();

    @Nonnull
    default InstitutionProto.InstitutionRecordSet.Builder toProtoBuilder() {
        return InstitutionProto.InstitutionRecordSet.newBuilder()
                .setId(this.recordSetId().value())
                .setName(this.name())
                .setCovers(this.date().toProto())
                .setNotes(MoreObjects.firstNonNull(this.notes(), ""));
    }

    @Nonnull
    @Override
    default InstitutionProto.InstitutionRecordSet toProto() {
        return this.toProtoBuilder().build();
    }

}
