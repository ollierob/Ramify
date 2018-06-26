package net.ramify.model.record.residence;

import net.ramify.model.person.event.Residence;
import net.ramify.model.place.address.Address;
import net.ramify.model.record.Record;

import javax.annotation.Nonnull;

public interface CensusRecord extends Record {

    @Nonnull
    Address address();

    @Nonnull
    default Residence residence() {
        return new Residence(this.date(), "Residence", this.address());
    }

}
