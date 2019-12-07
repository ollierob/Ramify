package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.type.LifeEventRecord;

import javax.annotation.Nonnull;

public abstract class GenericLifeEventRecord implements LifeEventRecord {

    private final RecordId recordId;
    private final RecordSet recordSet;
    private final Family family;
    private final DateRange date;

    protected GenericLifeEventRecord(
            final RecordId recordId,
            final RecordSet recordSet,
            final Family family,
            final DateRange date) {
        this.recordId = recordId;
        this.recordSet = recordSet;
        this.family = family;
        this.date = date;
    }

    @Nonnull
    @Override
    public Family family() {
        return family;
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

    @Nonnull
    @Override
    public RecordId recordId() {
        return recordId;
    }

    protected abstract EventProto.RecordType type();

    @Nonnull
    @Override
    public RecordProto.Record.Builder toProtoBuilder() {
        return LifeEventRecord.super.toProtoBuilder().setType(this.type());
    }

}
