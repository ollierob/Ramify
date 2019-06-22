package net.ramify.model.record;

import net.ramify.model.date.ExactDate;

import javax.annotation.Nonnull;

public abstract class AbstractRecord implements Record {

    private final RecordId id;
    private final ExactDate date;

    protected AbstractRecord(final RecordId id, final ExactDate date) {
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
    public ExactDate date() {
        return date;
    }

}
