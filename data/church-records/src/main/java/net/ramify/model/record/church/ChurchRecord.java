package net.ramify.model.record.church;

import net.ramify.model.place.address.Address;
import net.ramify.model.record.Record;

import javax.annotation.Nonnull;

public interface ChurchRecord extends Record {

    @Nonnull
    Address churchAddress();

}
