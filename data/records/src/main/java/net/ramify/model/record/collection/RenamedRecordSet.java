package net.ramify.model.record.collection;

import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;

public class RenamedRecordSet extends DelegatedRecordSet {

    private final String title;

    public RenamedRecordSet(final RecordSet delegate, final String title) {
        super(delegate);
        this.title = title;
    }

    @Nonnull
    @Override
    public String title() {
        return title;
    }

    @Nonnull
    @Override
    public RecordProto.RecordSet.Builder toProtoBuilder(final boolean includeParent) {
        return super.toProtoBuilder(includeParent).setTitle(title);
    }

}
