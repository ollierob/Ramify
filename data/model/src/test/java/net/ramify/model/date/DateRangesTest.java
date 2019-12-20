package net.ramify.model.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DateRangesTest {

    @Test
    void testIntersection() {

        final var d1 = ClosedDateRange.of(LocalDate.of(1799, 4, 8), LocalDate.of(1800, 4, 7));
        final var d2 = ClosedDateRange.of(LocalDate.of(1796, 1, 2), LocalDate.of(1801, 12, 31));

        final var intersection = DateRanges.intersection(d1, d2);
        assertNotNull(intersection);
        assertEquals(LocalDate.of(1799, 4, 8), intersection.earliestIsoDate());
        assertEquals(LocalDate.of(1800, 4, 7), intersection.latestIsoDate());

    }

}