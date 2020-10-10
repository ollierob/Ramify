package net.ramify.model.date;

abstract class AbstractDateRange implements DateRange {

    @Override
    public boolean equals(final Object that) {
        return that instanceof DateRange
                && (this == that || this.equals((DateRange) that));
    }

    protected boolean equals(final DateRange that) {
        return DateRanges.equals(this, that);
    }

    @Override
    public int hashCode() {
        return DateRanges.hash(this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "[" + this.earliestInclusive()
                + ":" + this.latestInclusive()
                + "]";
    }

}
