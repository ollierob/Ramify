package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;
import net.ramify.model.place.address.Address;

public class Residence extends AbstractEvent {

    public Residence(final DateRange date, final String description, final Address address) {
        super(date, description, address);
    }

}
