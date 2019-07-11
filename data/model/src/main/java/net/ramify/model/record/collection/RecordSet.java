package net.ramify.model.record.collection;

import com.google.common.base.MoreObjects;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.HasDate;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.record.HasTitleDescription;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 *
 */
public interface RecordSet extends HasTitleDescription, HasRecordSetId, HasDate, HasPlaceId, BuildsProto<RecordProto.RecordSet> {

    @CheckForNull
    RecordSetId parentId();

    @Nonnull
    default RecordProto.RecordSet.Builder toProtoBuilder() {
        return RecordProto.RecordSet.newBuilder()
                .setId(this.recordSetId().value())
                .setTitle(this.title())
                .setDescription(MoreObjects.firstNonNull(this.description(), ""))
                .setParentId(Functions.ifNonNull(this.parentId(), RecordSetId::value, ""));
    }

    @Nonnull
    @Override
    default RecordProto.RecordSet toProto() {
        return this.toProtoBuilder().build();
    }

}
