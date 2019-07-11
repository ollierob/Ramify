package net.ramify.model.record;

import net.ramify.model.date.ExactDate;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;

public abstract class AbstractRecord implements Record {

    private final RecordId id;
    private final ExactDate date;

    protected AbstractRecord(final RecordId id, final ExactDate date) {
        this.id = id;
        this.date = date;
    }

    @Nonnull
    @Override
    public RecordId recordId() {
        return id;
    }

    @Nonnull
    @Override
    public ExactDate date() {
        return date;
    }

    protected abstract RecordProto.RecordType protoType();

    @Nonnull
    @Override
    public RecordProto.Record.Builder toProtoBuilder() {
        return Record.super.toProtoBuilder()
                .setType(this.protoType());
    }

}
