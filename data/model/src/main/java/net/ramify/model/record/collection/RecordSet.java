package net.ramify.model.record.collection;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.HasDate;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.record.HasTitleDescription;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.utils.objects.Consumers;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

/**
 *
 */
public interface RecordSet extends HasTitleDescription, HasRecordSetId, HasDate, HasPlaceId, BuildsProto<RecordProto.RecordSet> {

    @CheckForNull
    RecordSetId parentId();

    @Nonnull
    Set<RecordSetReference> references();

    @Nonnull
    default RecordProto.RecordSet.Builder toProtoBuilder() {
        final var builder = RecordProto.RecordSet.newBuilder()
                .setId(this.recordSetId().value())
                .setTitle(this.title())
                .setDescription(MoreObjects.firstNonNull(this.description(), ""))
                .setParentId(Functions.ifNonNull(this.parentId(), RecordSetId::value, ""))
                .addAllExternalReference(Iterables.transform(this.references(), RecordSetReference::toProto));
        Consumers.ifNonNull(this.date(), date -> builder.setDate(date.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default RecordProto.RecordSet toProto() {
        return this.toProtoBuilder().build();
    }

}
