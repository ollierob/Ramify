package net.ramify.model.record.census;

import net.ramify.model.event.DateRange;
import net.ramify.model.place.address.Address;

import javax.annotation.Nonnull;

public abstract class AbstractCensusRecord implements CensusRecord {

    private final DateRange date;
    private final Address address;

    protected AbstractCensusRecord(DateRange date, Address address) {
        this.date = date;
        this.address = address;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @Nonnull
    @Override
    public Address address() {
        return address;
    }

}
