package net.ramify.strategy.merge.date;

import net.ramify.model.date.DateRange;

import java.util.Optional;

class DatesIntersect implements DateMerge {

    @Override
    public Optional<DateRange> merge(final DateRange t1, final DateRange t2) {
        return t1.intersection(t2);
    }

}
