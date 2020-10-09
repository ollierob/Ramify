package net.ramify.model.family;

import net.ramify.model.date.AfterDate;
import net.ramify.model.date.ApproximateDateRange;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.date.InYears;
import net.ramify.model.date.parse.DateRangeParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GedcomDateRangeParserTest {

    @Mock
    private DateRangeParser mockDelegate;
    @InjectMocks
    private GedcomDateRangeParser testParser;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGedcomRange_Approximate() {

        when(mockDelegate.get("1887")).thenReturn(new InYears(1887));

        {
            final var range = testParser.get("A/+1887");
            assertEquals(BeforeDate.strictlyBefore(LocalDate.of(1887, Month.JANUARY, 1)).approximately(), range);
        }

        {
            final var range = testParser.get("A+1887/");
            assertEquals(AfterDate.strictlyAfter(LocalDate.of(1887, Month.DECEMBER, 31)).approximately(), range);
        }

        when(mockDelegate.get("1889")).thenReturn(new InYears(1889));

        {
            final var range = testParser.get("A+1887/+1889");
            assertEquals(ApproximateDateRange.of(ClosedDateRange.of(LocalDate.of(1887, Month.JANUARY, 1), LocalDate.of(1889, Month.DECEMBER, 31))), range);
        }

    }

}