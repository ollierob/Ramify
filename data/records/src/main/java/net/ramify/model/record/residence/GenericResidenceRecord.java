package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.ResidenceRecord;

public class GenericResidenceRecord extends GenericLifeEventRecord implements ResidenceRecord {

    public GenericResidenceRecord(RecordId recordId, RecordSet recordSet, Family family, DateRange date) {
        super(recordId, recordSet, family, date);
    }

}
