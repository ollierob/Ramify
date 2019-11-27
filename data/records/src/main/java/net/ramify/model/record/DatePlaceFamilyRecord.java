package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;

import javax.annotation.Nonnull;

public abstract class DatePlaceFamilyRecord extends DateRecord implements HasPlaceId, SingleFamilyRecord {

    private final PlaceId placeId;
    private final Family family;

    protected DatePlaceFamilyRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date,
            final PlaceId placeId,
            final Family family) {
        super(id, recordSet, date);
        this.placeId = placeId;
        this.family = family;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return placeId;
    }

    @Nonnull
    @Override
    public Family family() {
        return family;
    }

}
