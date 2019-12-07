package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.place.Place;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.type.ResidenceRecord;
import net.ramify.utils.objects.Consumers;

import javax.annotation.Nonnull;

public class GenericResidenceRecord extends GenericLifeEventRecord implements ResidenceRecord {

    private final Place place;

    public GenericResidenceRecord(RecordId recordId, RecordSet recordSet, Family family, DateRange date, final Place place) {
        super(recordId, recordSet, family, date);
        this.place = place;
    }

    @Nonnull
    @Override
    public RecordProto.Record.Builder toProtoBuilder() {
        final var builder = ResidenceRecord.super.toProtoBuilder();
        Consumers.ifNonNull(place, p -> builder.setPlace(p.toProto(false)));
        return builder;
    }

}
