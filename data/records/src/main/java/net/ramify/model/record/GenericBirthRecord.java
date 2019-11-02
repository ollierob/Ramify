package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.place.Place;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.BirthRecord;

import javax.annotation.Nonnull;

public class GenericBirthRecord extends DateRecord implements BirthRecord {

    private final Place birthPlace;
    private final Family family;

    public GenericBirthRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date,
            final Place birthPlace,
            final Family family) {
        super(id, recordSet, date);
        this.birthPlace = birthPlace;
        this.family = family;
    }

    @Nonnull
    @Override
    public Family family() {
        return family;
    }

    @Nonnull
    @Override
    public Place place() {
        return birthPlace;
    }

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.BIRTH;
    }

}
