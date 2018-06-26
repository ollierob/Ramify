package net.ramify.model.record.residence;

import net.ramify.model.event.Residence;
import net.ramify.model.place.address.Address;
import net.ramify.model.record.SingleFamilyRecord;

import javax.annotation.Nonnull;

public interface CensusRecord extends SingleFamilyRecord {

    @Nonnull
    Address address();

    @Nonnull
    default Residence residence() {
        return new Residence(this.date(), "Residence", this.address());
    }

}
