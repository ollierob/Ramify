package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;
import net.ramify.model.place.address.Address;

public class Burial extends AbstractEvent {

    public Burial(DateRange date, String description, Address address) {
        super(date, description, address);
    }

}
