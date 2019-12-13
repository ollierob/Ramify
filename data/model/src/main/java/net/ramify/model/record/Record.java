package net.ramify.model.record;

import com.google.common.collect.Iterables;
import net.meerkat.objects.Castable;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.HasDate;
import net.ramify.model.family.Family;
import net.ramify.model.family.collection.HasFamilies;
import net.ramify.model.place.HasPlace;
import net.ramify.model.record.collection.HasRecordSet;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.type.RecordHandler;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public interface Record extends HasRecordId, HasRecordSet, HasDate, HasFamilies, Castable<Record>, BuildsProto<RecordProto.Record> {

    @Nonnull
    default Stream<? extends IndividualRecord> individualRecords() {
        return this.people().stream().map(person -> IndividualRecord.of(this, person));
    }

    <R> R handleWith(@Nonnull RecordHandler<R> handler);

    @Nonnull
    default RecordProto.Record.Builder toProtoBuilder() {
        final var builder = RecordProto.Record.newBuilder()
                .setId(this.recordId().value())
                .setDate(this.date().toProto())
                .setRecordSetId(this.recordSetId().value())
                .addAllFamily(Iterables.transform(this.families(), Family::toProto));
        if (this instanceof HasPlace) builder.setPlace(((HasPlace) this).place().toProto(false));
        return builder;
    }

    @Nonnull
    @Override
    default RecordProto.Record toProto() {
        return this.toProtoBuilder().build();
    }

}
