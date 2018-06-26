package net.ramify.model.event;

import net.ramify.model.date.DateRange;

import javax.annotation.Nonnull;

public class AbstractLogicalEvent implements Event {

    private final DateRange date;
    private final String description;

    protected AbstractLogicalEvent(final DateRange date, final String description) {
        this.date = date;
        this.description = description;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @Nonnull
    @Override
    public String description() {
        return description;
    }

}
