package net.ramify.model.person.age;

import net.meerkat.temporal.date.Periods;
import net.ollie.protobuf.BuildsProto;
import net.ramify.model.date.DateRange;
import net.ramify.model.person.proto.AgeProto;

import javax.annotation.Nonnull;
import java.time.Period;
import java.time.Year;
import java.util.Optional;

public interface Age extends BuildsProto<AgeProto.Age> {

    Age ZERO = exactly(Period.ZERO);

    /**
     * @return normalized lower bound (min age)
     */
    @Nonnull
    Period lowerBound();

    /**
     * @return normalized upper bound (max age)
     */
    @Nonnull
    Period upperBound();

    @Nonnull
    default Optional<Period> exact() {
        final var lower = this.lowerBound().normalized();
        final var upper = this.upperBound().normalized();
        return lower.equals(upper) ? Optional.of(upper) : Optional.empty();
    }

    @Nonnull
    default Optional<Year> exactYears() {
        final var lower = this.lowerBound().getYears();
        final var upper = this.upperBound().getYears();
        return lower == upper ? Optional.of(Year.of(lower)) : Optional.empty();
    }

    @Nonnull
    default DateRange birthDate(final DateRange date) {
        final var earliest = date.earliestInclusive().map(d -> d.minus(this.upperBound()));
        final var latest = date.latestInclusive().map(d -> d.minus(this.lowerBound()));
        return new AgeDateRange(earliest, latest);
    }

    default boolean isSame(final Period period) {
        return Periods.approximateCompare(this.lowerBound(), period) <= 0
                && Periods.approximateCompare(period, this.upperBound()) <= 0;
    }

    default boolean isSameOrOlderThan(final Period period) {
        return Periods.approximateCompare(period, this.upperBound()) <= 0;
    }

    @Nonnull
    default AgeProto.Age.Builder toProtoBuilder() {
        return AgeProto.Age.newBuilder()
                .setMin(toProto(this.lowerBound()))
                .setMax(toProto(this.upperBound()));
    }

    @Nonnull
    @Override
    default AgeProto.Age toProto() {
        return this.toProtoBuilder().build();
    }

    static Age exactly(@Nonnull final Period period) {
        return new PeriodBasedAge(period);
    }

    static Age ofYears(final int years) {
        return new RoundedDownAge(years);
    }

    static Age betweenInclusive(final int minYears, final int maxYears) {
        if (minYears > maxYears) return betweenInclusive(maxYears, minYears);
        if (minYears == maxYears) return exactly(Period.ofYears(minYears));
        return new PeriodBasedAge(Period.ofYears(minYears), Period.ofYears(maxYears));
    }

    static Age betweenExclusive(final int minYears, final int maxYears) {
        return new PeriodBasedAge(Period.ofYears(minYears), Period.of(maxYears, 0, -1));
    }

    static Age betweenInclusive(final Period min, final Period max) {
        return new PeriodBasedAge(min, max);
    }

    static DateRange birthDate(final int age, final DateRange on) {
        return Age.ofYears(age).birthDate(on);
    }

    static DateRange birthDate(final Period age, final DateRange on) {
        return Age.exactly(age).birthDate(on);
    }

    static Age fromDates(final DateRange birthDate, final DateRange ageDate) {
        return DateRangeAge.of(birthDate, ageDate);
    }

    static AgeProto.Period toProto(final Period period) {
        return AgeProto.Period.newBuilder()
                .setYears(period.getYears())
                .setMonths(period.getMonths())
                .setDays(period.getDays())
                .build();
    }

}
