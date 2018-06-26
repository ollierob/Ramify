package net.ramify.model.record.death;

import net.ramify.model.person.event.Death;
import net.ramify.model.record.Record;

public interface DeathRecord extends Record {

    Death death();

}
