package net.ramify.model.record;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.HasDate;
import net.ramify.model.person.HasPerson;
import net.ramify.model.person.Person;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;

public interface IndividualRecord extends HasPerson, HasDate, HasRecordSetId, BuildsProto<RecordProto.IndividualRecord> {

    @Nonnull
    default RecordProto.IndividualRecord.Builder toProtoBuilder() {
        return RecordProto.IndividualRecord.newBuilder()
                .setPerson(this.person().toProto())
                .setRecordSetId(this.recordSetId().value())
                .setDate(this.date().toProto());
    }

    @Nonnull
    @Override
    default RecordProto.IndividualRecord toProto() {
        return this.toProtoBuilder().build();
    }

    static IndividualRecord of(final Record record, final Person person) {

        final var recordProto = record.toProtoBuilder();

        return new IndividualRecord() {

            @Nonnull
            @Override
            public DateRange date() {
                return record.date();
            }

            @Nonnull
            @Override
            public RecordSetId recordSetId() {
                return record.recordSetId();
            }

            @Nonnull
            @Override
            public Person person() {
                return person;
            }

            @Nonnull
            @Override
            public RecordProto.IndividualRecord.Builder toProtoBuilder() {
                return RecordProto.IndividualRecord.newBuilder()
                        .setPerson(person.toProto())
                        .setDate(recordProto.getDate())
                        .setRecordSetId(recordProto.getRecordSetId())
                        .setType(recordProto.getType());
            }

        };

    }

}
