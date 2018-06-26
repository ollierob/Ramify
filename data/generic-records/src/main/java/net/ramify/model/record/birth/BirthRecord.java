package net.ramify.model.record.birth;

import net.ramify.model.person.event.Birth;
import net.ramify.model.place.address.HasAddress;
import net.ramify.model.record.Record;

import javax.annotation.Nonnull;

public interface BirthRecord extends Record, HasAddress {

    @Nonnull
    default Birth birth() {
        return new Birth(this.date(), this.address());
    }

}
