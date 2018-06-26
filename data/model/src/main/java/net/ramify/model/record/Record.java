package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Histories;
import net.ramify.model.family.Families;

import javax.annotation.Nonnull;
import java.util.Comparator;

public interface Record {

    @Nonnull
    DateRange date();

    @Nonnull
    Histories histories();

    @Nonnull
    Families families();

    Comparator<Record> COMPARE_BY_DATE = Comparator.comparing(Record::date, DateRange.COMPARE_BY_EARLIEST);

}
