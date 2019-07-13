package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.person.Person;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.type.ResidenceRecord;

import javax.annotation.Nonnull;

public class SinglePersonResidenceRecord implements ResidenceRecord {

    private final RecordId recordId;
    private final PlaceId placeId;
    private final Person person;
    private final DateRange date;

    public SinglePersonResidenceRecord(
            final RecordId recordId,
            final PlaceId placeId,
            final Person person,
            final DateRange date) {
        this.recordId = recordId;
        this.placeId = placeId;
        this.person = person;
        this.date = date;
    }

    @Nonnull
    @Override
    public RecordId recordId() {
        return recordId;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return placeId;
    }

    @Nonnull
    @Override
    public Family family() {
        return new SinglePersonFamily(person);
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

}
