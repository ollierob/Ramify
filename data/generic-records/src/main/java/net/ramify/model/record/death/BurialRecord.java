package net.ramify.model.record.death;

import net.ramify.model.person.event.Burial;
import net.ramify.model.record.Record;

public interface BurialRecord extends Record {

    Burial burial();

}
