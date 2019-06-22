package net.ramify.model.record;

import net.ramify.model.date.DateRange;

import javax.annotation.Nonnull;

public abstract class AbstractRecord implements Record {

    private final RecordId id;
    private final DateRange date;

    protected AbstractRecord(final RecordId id, final DateRange date) {
        this.id = id;
        this.date = date;
    }

    @Nonnull
    @Override
    public RecordId recordId() {
        return id;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

}
