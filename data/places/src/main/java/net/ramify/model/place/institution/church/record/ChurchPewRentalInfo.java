package net.ramify.model.place.institution.church.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.record.collection.RecordSetId;

import javax.annotation.Nonnull;

public class ChurchPewRentalInfo extends AbstractChurchRecordSetInfo {

    public ChurchPewRentalInfo(final RecordSetId id, final DateRange date, final String notes) {
        super(id, date, notes);
    }

    @Nonnull
    @Override
    public String name() {
        return "Pew rentals";
    }
}
