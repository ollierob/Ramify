package net.ramify.model.record.collection;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.HasDate;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.HasTitleDescription;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

/**
 *
 */
public interface RecordSet extends HasTitleDescription, HasRecordSetId, HasDate, HasPlaceId, BuildsProto<RecordProto.RecordSet> {

    @CheckForNull
    RecordSet parent();

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

    int size();

    @Nonnull
    default RecordProto.RecordSet.Builder toProtoBuilder(final boolean includeParent) {
        final var builder = RecordProto.RecordSet.newBuilder()
                .setId(this.recordSetId().value())
                .setLongTitle(this.title())
                .setDescription(MoreObjects.firstNonNull(this.description(), ""))
                .setCoversPlaceId(this.covers().value())
                .setNumRecords(this.size())
                .addAllExternalReference(Iterables.transform(this.references(), RecordSetReference::toProto));
        if (includeParent) Consumers.ifNonNull(this.parent(), parent -> builder.setParent(parent.toProto()));
        Consumers.ifNonNull(this.createdBy(), place -> builder.setCreatorPlaceId(place.value()));
        Consumers.ifNonNull(this.date(), date -> builder.setDate(date.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default RecordProto.RecordSet toProto() {
        return this.toProtoBuilder(true).build();
    }

}
