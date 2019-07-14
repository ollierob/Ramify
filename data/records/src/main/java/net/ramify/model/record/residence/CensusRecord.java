package net.ramify.model.record.residence;

import net.ramify.model.date.ExactDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.Place;
import net.ramify.model.record.ExactDateRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.type.ResidenceRecord;

import javax.annotation.Nonnull;

public abstract class CensusRecord extends ExactDateRecord implements ResidenceRecord {

    private final Place place;

    protected CensusRecord(final RecordId id, final ExactDate date, final Place place) {
        super(id, date);
        this.place = place;
    }

    @Nonnull
    @Override
    public Place place() {
        return place;
    }

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.RESIDENCE;
    }

}
