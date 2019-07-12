package net.ramify.model.record.collection;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

public abstract class DelegatedRecordSet implements RecordSet {

    private final RecordSet delegate;

    protected DelegatedRecordSet(final RecordSet delegate) {
        this.delegate = delegate;
    }

    @CheckForNull
    @Override
    public RecordSetId parentId() {
        return delegate.parentId();
    }

    @Nonnull
    @Override
    public Set<RecordSetReference> references() {
        return delegate.references();
    }

    @Nonnull
    @Override
    public DateRange date() {
        return delegate.date();
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return delegate.placeId();
    }

    @Nonnull
    @Override
    public String title() {
        return delegate.title();
    }

    @CheckForNull
    @Override
    public String description() {
        return delegate.description();
    }

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return delegate.recordSetId();
    }

    @Nonnull
    @Override
    public RecordProto.RecordSet.Builder toProtoBuilder() {
        return delegate.toProtoBuilder();
    }

}
