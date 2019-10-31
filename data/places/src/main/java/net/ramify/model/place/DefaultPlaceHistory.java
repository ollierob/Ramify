package net.ramify.model.place;

import net.ramify.model.date.DateRange;

import javax.annotation.CheckForNull;

public class DefaultPlaceHistory implements PlaceHistory {

    private final DateRange open, close;

    public DefaultPlaceHistory(final DateRange open, final DateRange close) {
        this.open = open;
        this.close = close;
    }

    @CheckForNull
    @Override
    public DateRange opened() {
        return open;
    }

    @CheckForNull
    @Override
    public DateRange closed() {
        return close;
    }

}
