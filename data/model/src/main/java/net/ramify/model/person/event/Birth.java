package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;
import net.ramify.model.place.address.Address;

public class Birth extends AbstractPhysicalEvent {

    public Birth(final DateRange date, final Address address) {
        this(date, "Birth", address);
    }

    public Birth(final DateRange date, final String description, final Address address) {
        super(date, description, address);
    }

}
