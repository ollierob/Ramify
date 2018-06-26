package net.ramify.model.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.address.Address;

public class Death extends AbstractPhysicalEvent {

    public Death(final DateRange date) {
        this(date, Address.UNKNOWN);
    }

    public Death(final DateRange date, final Address address) {
        this(date, "Death", address);
    }

    public Death(final DateRange date, final String description, final Address address) {
        super(date, description, address);
    }

    @Override
    public boolean unique() {
        return true;
    }

}
