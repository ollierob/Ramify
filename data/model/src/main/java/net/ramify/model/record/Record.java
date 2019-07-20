package net.ramify.model.record;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.HasDate;
import net.ramify.model.family.Family;
import net.ramify.model.family.collection.HasFamilies;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.type.RecordHandler;
import net.ramify.utils.objects.Castable;

import javax.annotation.Nonnull;

public interface Record extends HasRecordId, HasRecordSetId, HasDate, HasFamilies, Castable<Record>, BuildsProto<RecordProto.Record> {

    <R> R handleWith(@Nonnull RecordHandler<R> handler);

    @Nonnull
    default RecordProto.Record.Builder toProtoBuilder() {
        return RecordProto.Record.newBuilder()
                .setId(this.recordId().value())
                .setDate(this.date().toProto())
                .setRecordSetId(this.recordSetId().value())
                .addAllFamily(Iterables.transform(this.families(), Family::toProto));
    }

    @Nonnull
    @Override
    default RecordProto.Record toProto() {
        return this.toProtoBuilder().build();
    }

}
