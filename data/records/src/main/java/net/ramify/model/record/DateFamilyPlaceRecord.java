package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;

import javax.annotation.Nonnull;

public abstract class DateFamilyPlaceRecord extends DateFamilyRecord implements HasPlaceId {

    private final PlaceId placeId;

    protected DateFamilyPlaceRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date,
            final Family family,
            final PlaceId placeId) {
        super(id, recordSet, date, family);
        this.placeId = placeId;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return placeId;
    }

}
