package net.ramify.model.record.residence;

import net.meerkat.functions.consumers.Consumers;
import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.place.Place;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.type.ResidenceRecord;

import javax.annotation.Nonnull;

public class GenericResidenceRecord extends GenericLifeEventRecord implements ResidenceRecord {

    private final Place place;

    public GenericResidenceRecord(final RecordId recordId, final RecordSet recordSet, final Family family, final DateRange date, final Place place) {
        super(recordId, recordSet, family, date);
        this.place = place;
    }

    @Nonnull
    @Override
    public RecordProto.Record.Builder toProtoBuilder() {
        final var builder = ResidenceRecord.super.toProtoBuilder();
        Consumers.ifNonNull(place, p -> builder.setPlace(p.toProto()));
        return builder;
    }

}
