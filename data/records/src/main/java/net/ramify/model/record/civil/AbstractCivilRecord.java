package net.ramify.model.record.civil;

import net.ramify.model.date.ExactDate;
import net.ramify.model.record.ExactDateRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.SingleFamilyRecord;

public abstract class AbstractCivilRecord extends ExactDateRecord implements SingleFamilyRecord {

    protected AbstractCivilRecord(final RecordId id, final ExactDate eventDate) {
        super(id, eventDate);
    }

}
