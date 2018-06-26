package net.ramify.model.person.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.address.Address;

public class Divorce extends AbstractPhysicalEvent {

    public Divorce(final DateRange date, final String description, final Address address) {
        super(date, description, address);
    }

}
