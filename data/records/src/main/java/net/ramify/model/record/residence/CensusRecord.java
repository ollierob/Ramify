package net.ramify.model.record.residence;

import net.ramify.model.date.ExactDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.ExactDateRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.type.ResidenceRecord;

import javax.annotation.Nonnull;

public abstract class CensusRecord extends ExactDateRecord implements ResidenceRecord {

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

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.RESIDENCE;
    }

}
