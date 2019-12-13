package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;

public abstract class DateRecord implements Record {

    private final RecordId id;
    private final DateRange date;
    private final RecordSet recordSet;

    protected DateRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date) {
        this.id = id;
        this.date = date;
        this.recordSet = recordSet;
    }

    @Nonnull
    @Override
    public RecordId recordId() {
        return id;
    }

    @Nonnull
    @Override
    public RecordSet recordSet() {
        return recordSet;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    protected abstract EventProto.RecordType protoType();

    protected EventBuilder eventBuilder() {
        return EventBuilder.builderWithRandomId(this.date());
    }

    @Nonnull
    @Override
    public RecordProto.Record.Builder toProtoBuilder() {
        return Record.super.toProtoBuilder().setType(this.protoType());
    }

}
