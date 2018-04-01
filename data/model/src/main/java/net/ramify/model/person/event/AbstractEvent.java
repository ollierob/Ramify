package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.place.address.Address;

import javax.annotation.Nonnull;

public class AbstractEvent implements Event {

    private final DateRange date;
    private final String description;
    private final Address address;

    protected AbstractEvent(final DateRange date, final String description, final Address address) {
        this.date = date;
        this.description = description;
        this.address = address;
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

    @Nonnull
    @Override
    public Address address() {
        return address;
    }

}
