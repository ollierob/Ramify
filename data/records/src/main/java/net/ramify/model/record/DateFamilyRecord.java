package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.record.collection.RecordSet;

import javax.annotation.Nonnull;

public abstract class DateFamilyRecord extends DatedRecord implements SingleFamilyRecord {

    private final Family family;

    protected DateFamilyRecord(RecordId id, RecordSet recordSet, DateRange date, Family family) {
        super(id, recordSet, date);
        this.family = family;
    }

    @Nonnull
    @Override
    public Family family() {
        return family;
    }

}
