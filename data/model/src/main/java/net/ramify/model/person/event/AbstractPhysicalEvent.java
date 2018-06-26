package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;
import net.ramify.model.place.address.Address;
import net.ramify.model.place.address.HasAddress;

import javax.annotation.Nonnull;

public class AbstractPhysicalEvent extends AbstractLogicalEvent implements HasAddress {

    private final Address address;

    protected AbstractPhysicalEvent(final DateRange date, final String description, final Address address) {
        super(date, description);
        this.address = address;
    }

    @Nonnull
    @Override
    public Address address() {
        return address;
    }

}
