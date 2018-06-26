package net.ramify.model.person.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.address.Address;

public class Death extends AbstractPhysicalEvent {

    public Death(final DateRange date, final Address address) {
        this(date, "Death", address);
    }

    public Death(final DateRange date, final String description, final Address address) {
        super(date, description, address);
    }

}
