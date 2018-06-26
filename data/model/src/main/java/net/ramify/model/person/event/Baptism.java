package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;
import net.ramify.model.place.address.Address;

public class Baptism extends AbstractEvent {

    public Baptism(final DateRange date, final Address address) {
        super(date, "Baptism", address);
    }

}
