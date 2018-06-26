package net.ramify.model.record.residence;

import net.ramify.model.place.address.Address;
import net.ramify.model.place.address.HasAddress;
import net.ramify.model.record.Record;

public interface ResidenceRecord extends Record, HasAddress {

    @Override
    Address address();
    
}
