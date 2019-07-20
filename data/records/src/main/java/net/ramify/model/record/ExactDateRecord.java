package net.ramify.model.record;

import net.ramify.model.date.ExactDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;

public abstract class ExactDateRecord implements Record {

    private final RecordId id;
    private final ExactDate date;
    private final RecordSetId recordSetId;

    protected ExactDateRecord(
            final RecordId id,
            final RecordSetId recordSetId,
            final ExactDate date) {
        this.id = id;
        this.date = date;
        this.recordSetId = recordSetId;
    }

    @Nonnull
    @Override
    public RecordId recordId() {
        return id;
    }

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return recordSetId;
    }

    @Nonnull
    @Override
    public ExactDate date() {
        return date;
    }

    protected abstract EventProto.RecordType protoType();

    @Nonnull
    @Override
    public RecordProto.Record.Builder toProtoBuilder() {
        return Record.super.toProtoBuilder()
                .setType(this.protoType());
    }

}
