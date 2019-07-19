package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.type.LifeEventRecord;

import javax.annotation.Nonnull;

public class GenericLifeEventRecord implements LifeEventRecord {

    private final RecordId recordId;
    private final Family family;
    private final DateRange date;

    public GenericLifeEventRecord(RecordId recordId, Family family, DateRange date) {
        this.recordId = recordId;
        this.family = family;
        this.date = date;
    }

    @Nonnull
    @Override
    public Family family() {
        return family;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @Nonnull
    @Override
    public RecordId recordId() {
        return recordId;
    }

}
