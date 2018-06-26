package net.ramify.model.person.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.address.Address;

public class Baptism extends AbstractPhysicalEvent {

    public Baptism(final DateRange date, final Address address) {
        super(date, "Baptism", address);
    }

}
