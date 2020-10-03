package net.ramify.model.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeforeDateTest {

    @Test
    void shouldIntersect() {

        final var date = LocalDate.of(2020, 10, 3);
        final var before = BeforeDate.strictlyBefore(date);

        assertEquals(Optional.of(before), before.intersection(before));

        {
            final var newBefore = BeforeDate.strictlyBefore(date.minusDays(1));
            assertEquals(Optional.of(newBefore), before.intersection(newBefore));
        }

    }

}