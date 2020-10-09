package net.ramify.model.date;

import net.ramify.model.date.proto.DateProto;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class ApproximateDateRange extends AbstractDateRange {

    public static DateRange of(final DateRange range) {
        return range.isApproximate() ? range : new ApproximateDateRange(range);
    }

    private final DateRange delegate;

    ApproximateDateRange(final DateRange delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean isApproximate() {
        return !delegate.isExact();
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> earliestInclusive() {
        return delegate.earliestInclusive();
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return delegate.latestInclusive();
    }

    @Nonnull
    @Override
    public DateProto.DateRange.Builder toProtoBuilder() {
        return delegate.toProtoBuilder()
                .setApproximate(this.isApproximate());
    }

    @Override
    public String toString() {
        return "Approximately[" + delegate + "]";
    }

}
