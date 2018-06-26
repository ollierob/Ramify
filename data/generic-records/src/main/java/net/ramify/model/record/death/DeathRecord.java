package net.ramify.model.record.death;

import net.ramify.model.event.Death;
import net.ramify.model.place.address.HasAddress;
import net.ramify.model.record.Record;

public interface DeathRecord extends Record, HasAddress {

    default Death death() {
        return new Death(this.date(), this.address());
    }

}
