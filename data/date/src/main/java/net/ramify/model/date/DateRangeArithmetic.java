package net.ramify.model.date;

import java.time.Period;

interface DateRangeArithmetic {

    default DateRange minusYears(final int years) {
        return this.minus(Period.ofYears(years));
    }

    default DateRange minus(final Period period) {
        return this.plus(period.negated());
    }

    default DateRange plusYears(final int years) {
        return this.plus(Period.ofYears(years));
    }

    DateRange plus(Period period);

}
