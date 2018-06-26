package net.ramify.model.record.death;

import net.ramify.model.person.event.Burial;
import net.ramify.model.place.address.HasAddress;
import net.ramify.model.record.Record;

import javax.annotation.Nonnull;

public interface BurialRecord extends Record, HasAddress {

    @Nonnull
    default Burial burial() {
        return new Burial(this.date(), this.address());
    }

}
