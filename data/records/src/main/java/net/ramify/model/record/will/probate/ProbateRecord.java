package net.ramify.model.record.will.probate;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.record.DateFamilyRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.RecordSetTypes;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetType;
import net.ramify.model.record.type.PostDeathRecord;

import javax.annotation.Nonnull;

public class ProbateRecord extends DateFamilyRecord implements PostDeathRecord {

    public ProbateRecord(final RecordId id, final RecordSet recordSet, final DateRange date, final Family family) {
        super(id, recordSet, date, family);
    }

    @Nonnull
    @Override
    public RecordSetType type() {
        return RecordSetTypes.PROBATE;
    }

}
