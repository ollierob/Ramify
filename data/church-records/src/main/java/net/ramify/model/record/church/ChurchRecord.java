package net.ramify.model.record.church;

import net.ramify.model.place.address.Address;
import net.ramify.model.place.address.HasAddress;
import net.ramify.model.record.Record;

import javax.annotation.Nonnull;

public interface ChurchRecord extends Record, HasAddress {

    /**
     * @return the church address.
     */
    @Nonnull
    Address address();

}
