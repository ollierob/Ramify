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

    @Nonnull
    default LocalDate earliestIsoDate() {
        return this.earliestInclusive().map(LocalDate::from).orElse(LocalDate.MIN);
    }

    @Nonnull
    default LocalDate latestIsoDate() {
        return this.latestInclusive().map(LocalDate::from).orElse(LocalDate.MAX);
    }

    @Nonnull
    default Optional<? extends ChronoLocalDate> exact() {
        final var earliest = this.earliestInclusive().orElse(null);
        if (earliest == null) return Optional.empty();
        final var latest = this.latestInclusive().orElse(null);
        if (latest == null) return Optional.empty();
        return earliest.compareTo(latest) == 0 ? Optional.of(earliest) : Optional.empty();
    }

    default boolean isApproximate() {
        return false;
    }

    default boolean isExact() {
        return this.earliestIsoDate().compareTo(this.latestIsoDate()) == 0;
    }

    default boolean isFinite() {
        return this.earliestInclusive().isPresent() && this.latestInclusive().isPresent();
    }

    default boolean contains(@Nonnull final ChronoLocalDate date) {
        return this.earliestInclusive().map(DateRange::covariant).orElse(LocalDate.MIN).compareTo(date) <= 0
                && this.latestInclusive().map(DateRange::covariant).orElse(LocalDate.MAX).compareTo(date) >= 0;
    }

    default DateRange minusYears(final int years) {
        return this.minus(Period.ofYears(years));
    }

    default DateRange minus(final Period period) {
        return this.minus(period, period);
    }

    default DateRange minus(final Period max, final Period min) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Nonnull
    default Optional<DateRange> intersection(DateRange that) {
        return Optional.ofNullable(DateRanges.intersection(this, that));
    }

    default boolean intersects(final DateRange that) {
        return this.intersection(that).isPresent();
    }

    @Nonnull
    default DateProto.DateRange.Builder toProtoBuilder() {
        final var builder = DateProto.DateRange.newBuilder().setApproximate(this.isApproximate());
        this.earliestInclusive().ifPresent(d -> builder.setEarliest(DateProtoUtils.toProto(d)));
        this.latestInclusive().ifPresent(d -> builder.setLatest(DateProtoUtils.toProto(d)));
        return builder;
    }

    @Override
    default DateProto.DateRange toProto() {
        return this.toProtoBuilder().build();
    }

    static DateRange of(final ChronoLocalDate date) {
        return ExactDate.on(date);
    }

    static DateRange closed(final ChronoLocalDate d1, final ChronoLocalDate d2) {
        return ClosedDateRange.of(d1, d2);
    }

    static <T extends ChronoLocalDate> ChronoLocalDate covariant(final T date) {
        return date;
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

        @Nonnull
        @Override
        public Optional<DateRange> intersection(final DateRange that) {
            return Optional.of(that);
        }

        @Override
        public boolean intersects(final DateRange that) {
            return true;
        }

        @Override
        public String toString() {
            return "ALWAYS";
        }

    };

    DateRange NEVER = new DateRange() {

        @Nonnull
        @Override
        public Optional<? extends ChronoLocalDate> earliestInclusive() {
            return Optional.empty();
        }

        @Nonnull
        @Override
        public Optional<? extends ChronoLocalDate> latestInclusive() {
            return Optional.empty();
        }

        @Override
        public boolean contains(final ChronoLocalDate date) {
            return false;
        }

        @Nonnull
        @Override
        public Optional<DateRange> intersection(final DateRange that) {
            return Optional.empty();
        }

        @Override
        public boolean intersects(final DateRange that) {
            return false;
        }

        @Override
        public String toString() {
            return "NEVER";
        }
    };

}
