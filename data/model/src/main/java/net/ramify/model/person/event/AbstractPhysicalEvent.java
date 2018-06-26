package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.place.address.Address;
import net.ramify.model.place.address.HasAddress;

import javax.annotation.Nonnull;

public class AbstractPhysicalEvent implements Event, HasAddress {

    private final DateRange date;
    private final String description;
    private final Address address;

    protected AbstractPhysicalEvent(final DateRange date, final String description, final Address address) {
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
