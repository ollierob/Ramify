package net.ramify.model.record.census;

import net.ramify.model.date.ExactDate;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.AbstractRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.type.ResidenceRecord;

import javax.annotation.Nonnull;

public abstract class CensusRecord extends AbstractRecord implements ResidenceRecord {

    private final PlaceId placeId;

    protected CensusRecord(final RecordId id, final ExactDate date, PlaceId placeId) {
        super(id, date);
        this.placeId = placeId;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return placeId;
    }

}
