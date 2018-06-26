package net.ramify.model.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.place.address.Address;

public class Birth extends AbstractPhysicalEvent {

    public static Birth from(final AgeRange age, final DateRange ageDate) {
        return new Birth(ageDate.minus(age.max(), age.min()), Address.UNKNOWN);
    }

    public Birth(final DateRange date, final Address address) {
        this(date, "Birth", address);
    }

    public Birth(final DateRange date, final String description, final Address address) {
        super(date, description, address);
    }

    @Override
    public boolean unique() {
        return true;
    }

}
