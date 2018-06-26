package net.ramify.model.record.marriage;

import net.ramify.model.event.Marriage;
import net.ramify.model.place.address.HasAddress;
import net.ramify.model.record.SingleFamilyRecord;

import javax.annotation.Nonnull;

public interface MarriageRecord extends SingleFamilyRecord, HasAddress {

    @Nonnull
    default Marriage marriage() {
        return new Marriage(this.date(), this.address());
    }

}
