package net.ramify.model.date;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.proto.DateProto;
import net.ramify.model.date.proto.DateProtoUtils;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.util.Comparator;
import java.util.Optional;

public interface DateRange extends BuildsProto<DateProto.DateRange> {

    @Nonnull
    Optional<? extends ChronoLocalDate> earliestInclusive();

    @Nonnull
    Optional<? extends ChronoLocalDate> latestInclusive();

    default boolean isClosed() {
        return this.earliestInclusive().isPresent() && this.latestInclusive().isPresent();
    }

    default boolean contains(@Nonnull final ChronoLocalDate date) {
        return this.earliestInclusive().map(DateRange::covariant).orElse(LocalDate.MIN).compareTo(date) <= 0
                && this.latestInclusive().map(DateRange::covariant).orElse(LocalDate.MAX).compareTo(date) >= 0;
    }

    static ChronoLocalDate min(final ChronoLocalDate d1, final ChronoLocalDate d2) {
        return d1.isBefore(d2) ? d1 : d2;
    }

    static <T extends ChronoLocalDate> ChronoLocalDate covariant(final T date) {
        return date;
    }

    static ExactDate on(final ChronoLocalDate date) {
        return new ExactDate(date);
    }

    static DateRange before(final DateRange date) {
        throw new UnsupportedOperationException(); //TODO
    }

    static DateRange between(final ChronoLocalDate earliest, final ChronoLocalDate latest) {
        if (earliest.isAfter(latest)) return between(latest, earliest);
        return new ClosedDateRange(earliest, latest);
    }

    default DateRange minus(final Period period) {
        throw new UnsupportedOperationException(); //TODO
    }

    default DateRange minus(final Period max, final Period min) {
        throw new UnsupportedOperationException(); //TODO
    }

    default Optional<DateRange> intersection(final DateRange that) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Nonnull
    default DateProto.DateRange.Builder toProtoBuilder() {
        final var builder = DateProto.DateRange.newBuilder();
        this.earliestInclusive().ifPresent(d -> builder.setEarliest(DateProtoUtils.toProto(d)));
        this.latestInclusive().ifPresent(d -> builder.setLatest(DateProtoUtils.toProto(d)));
        return builder;
    }

    @Override
    default DateProto.DateRange toProto() {
        return this.toProtoBuilder().build();
    }

    Comparator<DateRange> COMPARE_BY_EARLIEST = (d1, d2) -> d1.earliestInclusive().flatMap(e1 -> d2.earliestInclusive().map(e1::compareTo)).orElse(0); //FIXME check if present

    DateRange ALWAYS = new DateRange() {

        @Override
        public Optional<? extends ChronoLocalDate> earliestInclusive() {
            return Optional.empty();
        }

        @Override
        public Optional<? extends ChronoLocalDate> latestInclusive() {
            return Optional.empty();
        }

        @Override
        public boolean contains(final ChronoLocalDate date) {
            return true;
        }

    };

}
