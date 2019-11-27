package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.DeathRecord;

public class GenericDeathRecord extends DatePlaceFamilyRecord implements DeathRecord {

    public GenericDeathRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date,
            final PlaceId deathPlace,
            final Family family) {
        super(id, recordSet, date, deathPlace, family);
    }

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.DEATH;
    }

}
