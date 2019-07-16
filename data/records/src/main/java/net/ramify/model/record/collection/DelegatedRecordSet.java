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
    public RecordSet parent() {
        return delegate.parent();
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

    @Override
    @CheckForNull
    public PlaceId createdBy() {
        return delegate.createdBy();
    }

    @Override
    @Nonnull
    public PlaceId covers() {
        return delegate.covers();
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

    @Override
    public int size() {
        return delegate.size();
    }

    @Nonnull
    @Override
    public RecordProto.RecordSet.Builder toProtoBuilder(final boolean includeParent) {
        return delegate.toProtoBuilder(includeParent);
    }

}
