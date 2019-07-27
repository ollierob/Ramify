package net.ramify.model.record.collection;

import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;

public class ResizedRecordSet extends DelegatedRecordSet {

    private final int size;

    public ResizedRecordSet(final RecordSet delegate, final int size) {
        super(delegate);
        this.size = size;
    }

    public int size() {
        return size;
    }

    @Nonnull
    @Override
    public RecordProto.RecordSet.Builder toProtoBuilder() {
        return super.toProtoBuilder().setNumRecords(size);
    }

}
