package net.ramify.model.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.address.Address;

public class Baptism extends AbstractPhysicalEvent {

    public Baptism(final DateRange date, final Address address) {
        super(date, "Baptism", address);
    }

    @Override
    public boolean unique() {
        return true;
    }

}
