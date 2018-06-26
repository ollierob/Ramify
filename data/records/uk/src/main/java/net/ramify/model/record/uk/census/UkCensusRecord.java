package net.ramify.model.record.uk.census;

import net.ramify.model.date.DateRange;
import net.ramify.model.record.residence.CensusRecord;

import java.time.LocalDate;
import java.time.Month;

public interface UkCensusRecord extends CensusRecord {

    DateRange CENSUS_1801 = DateRange.on(LocalDate.of(1801, Month.MARCH, 10));
    DateRange CENSUS_1811 = DateRange.on(LocalDate.of(1811, Month.MAY, 27));
    DateRange CENSUS_1821 = DateRange.on(LocalDate.of(1821, Month.MAY, 28));
    DateRange CENSUS_1831 = DateRange.on(LocalDate.of(1831, Month.MAY, 30));
    DateRange CENSUS_1841 = DateRange.on(LocalDate.of(1841, Month.JUNE, 6));
    DateRange CENSUS_1851 = DateRange.on(LocalDate.of(1851, Month.MARCH, 30));
    DateRange CENSUS_1861 = DateRange.on(LocalDate.of(1861, Month.APRIL, 7));

}
