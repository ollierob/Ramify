package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;
import net.ramify.model.place.address.Address;

public class Burial extends AbstractPhysicalEvent {

    public Burial(final DateRange date, final Address address) {
        super(date, "Burial", address);
    }

}
