package net.ramify.model.record.birth;

import net.ramify.model.person.event.Birth;
import net.ramify.model.record.Record;

public interface BirthRecord extends Record {

    Birth birth();

}
