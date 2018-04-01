package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;
import net.ramify.model.place.address.Address;

public class Baptism extends AbstractEvent {

    public Baptism(DateRange date, String description, Address address) {
        super(date, description, address);
    }

}
