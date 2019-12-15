package net.ramify.model.person.age;

import net.ramify.model.date.DateRange;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AgeTest {

    @Test
    void testContains() {

        final var age = Age.betweenInclusive(10, 15);
        assertFalse(age.isSame(Period.ofYears(9)));
        assertTrue(age.isSame(Period.ofYears(10)));
        assertTrue(age.isSame(Period.ofYears(12)));
        assertTrue(age.isSame(Period.ofYears(15)));
        assertFalse(age.isSame(Period.ofYears(16)));

    }

    @Test
    void testSameOrOlderThan() {

        final var p = Period.ofYears(16);

        assertFalse(Age.ofYears(2).isSameOrOlderThan(p));
        assertFalse(Age.betweenInclusive(10, 15).isSameOrOlderThan(p));
        assertTrue(Age.betweenInclusive(15, 20).isSameOrOlderThan(p));
        assertTrue(Age.betweenInclusive(20, 25).isSameOrOlderThan(p));

    }

    @Test
    void testBirthDate() {

        final var date = LocalDate.of(2019, 7, 19);

        final var dateRange = new DateRange() {

            @Nonnull
            @Override
            public Optional<? extends ChronoLocalDate> earliestInclusive() {
                return Optional.of(date);
            }

            @Nonnull
            @Override
            public Optional<? extends ChronoLocalDate> latestInclusive() {
                return Optional.of(date);
            }

            @Nonnull
            @Override
            public Optional<DateRange> intersection(DateRange that) {
                throw new UnsupportedOperationException(); //TODO
            }

        };

        final var birthDate = Age.birthDate(1, dateRange);
        assertEquals(LocalDate.of(2017, 7, 20), birthDate.earliestInclusive().orElse(null));
        assertEquals(LocalDate.of(2018, 7, 19), birthDate.latestInclusive().orElse(null));

    }

}