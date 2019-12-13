package net.ramify.model.record.collection;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import net.meerkat.functions.consumers.Consumers;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.HasDate;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.HasTitleDescription;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

/**
 *
 */
public interface RecordSet extends HasTitleDescription, HasRecordSet, HasDate, HasPlaceId, BuildsProto<RecordProto.RecordSet> {

    @Nonnull
    @Override
    default RecordSet recordSet() {
        return this;
    }

    @Override
    RecordSetId recordSetId();

    @Nonnull
    Set<RecordSetReference> references();

    @CheckForNull
    PlaceId createdBy();

    @Nonnull
    PlaceId covers();

    @Deprecated
    @Override
    default PlaceId placeId() {
        return this.covers();
    }

    int numRecords();

    int numIndividuals();

    @Nonnull
    default RecordProto.RecordSet.Builder toProtoBuilder() {
        final var builder = RecordProto.RecordSet.newBuilder()
                .setId(this.recordSetId().value())
                .setLongTitle(this.title())
                .setDescription(MoreObjects.firstNonNull(this.description(), ""))
                .setNumRecords(this.numRecords())
                .setNumIndividuals(this.numIndividuals())
                .addAllExternalReference(Iterables.transform(this.references(), RecordSetReference::toProto));
        Consumers.ifNonNull(this.createdBy(), place -> builder.setCreatorPlaceId(place.value()));
        Consumers.ifNonNull(this.date(), date -> builder.setDate(date.toProto()));
        Consumers.ifNonNull(this.covers(), covers -> builder.setCoversPlaceId(covers.value()));
        return builder;
    }

    @Nonnull
    @Override
    default RecordProto.RecordSet toProto() {
        return this.toProtoBuilder().build();
    }

}
