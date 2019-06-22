package net.ramify.model.record.civil;

import net.ramify.model.date.ExactDate;
import net.ramify.model.record.AbstractRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.SingleFamilyRecord;

public abstract class AbstractCivilRecord extends AbstractRecord implements SingleFamilyRecord {

    protected AbstractCivilRecord(RecordId id, ExactDate date) {
        super(id, date);
    }

}
