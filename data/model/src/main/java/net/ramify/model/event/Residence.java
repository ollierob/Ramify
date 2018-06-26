package net.ramify.model.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.address.Address;

public class Residence extends AbstractPhysicalEvent {

    public Residence(
            final DateRange date,
            final String description,
            final Address address) {
        super(date, description, address);
    }

    @Override
    public boolean unique() {
        return false;
    }

}
