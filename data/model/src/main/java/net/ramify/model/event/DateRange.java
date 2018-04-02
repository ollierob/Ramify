package net.ramify.model.event;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.util.Comparator;
import java.util.Optional;

public interface DateRange {

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

    static <T extends ChronoLocalDate> ChronoLocalDate covariant(final T date) {
        return date;
    }

    static DateRange before(final DateRange date) {
        throw new UnsupportedOperationException(); //TODO
    }

    default DateRange minus(final Period period) {
        throw new UnsupportedOperationException(); //TODO
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
