package net.ramify.model.place.institution.church.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.record.collection.RecordSetId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

abstract class AbstractChurchRecordSetInfo implements ChurchRecordSetInfo {

    private final RecordSetId id;
    private final DateRange date;
    private final String notes;

    AbstractChurchRecordSetInfo(RecordSetId id, DateRange date, String notes) {
        this.id = id;
        this.date = date;
        this.notes = notes;
    }

    @Nonnull
    @Override
    public RecordSetId recordSetId() {
        return id;
    }

    @CheckForNull
    @Override
    public String notes() {
        return notes;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

}
