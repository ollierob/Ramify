package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.person.Person;
import net.ramify.model.place.Place;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.type.ResidenceRecord;

import javax.annotation.Nonnull;

public class SinglePersonResidenceRecord implements ResidenceRecord {

    private final RecordId recordId;
    private final Place place;
    private final Person person;
    private final DateRange date;

    public SinglePersonResidenceRecord(
            final RecordId recordId,
            final Place place,
            final Person person,
            final DateRange date) {
        this.recordId = recordId;
        this.place = place;
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
    public Place place() {
        return place;
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
