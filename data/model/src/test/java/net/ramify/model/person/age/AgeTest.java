package net.ramify.model.person.age;

import net.ramify.model.date.DateRange;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AgeTest {

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

        };

        final var birthDate = Age.birthDate(1, dateRange);
        assertEquals(LocalDate.of(2017, 7, 20), birthDate.earliestInclusive().orElse(null));
        assertEquals(LocalDate.of(2018, 7, 19), birthDate.latestInclusive().orElse(null));

    }

}