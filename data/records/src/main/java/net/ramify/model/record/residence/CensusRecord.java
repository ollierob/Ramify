package net.ramify.model.record.residence;

import net.ramify.model.date.ExactDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;
import net.ramify.model.record.ExactDateRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.LifeEventRecord;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class CensusRecord extends ExactDateRecord implements LifeEventRecord, HasPlace {

    private final Place place;

    protected CensusRecord(
            final RecordId id,
            final RecordSet recordSet,
            final ExactDate date,
            final Place place) {
        super(id, recordSet, date);
        this.place = Objects.requireNonNull(place, "census place");
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
