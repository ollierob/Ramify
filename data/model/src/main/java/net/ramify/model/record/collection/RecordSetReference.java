package net.ramify.model.record.collection;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.util.Link;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface RecordSetReference extends BuildsProto<RecordProto.ExternalRecordReference> {

    @Nonnull
    String reference();

    @Nonnull
    String archive();

    @CheckForNull
    Link link();

    @Nonnull
    @Override
    default RecordProto.ExternalRecordReference toProto() {
        final var builder = RecordProto.ExternalRecordReference.newBuilder()
                .setReference(this.reference())
                .setArchive(this.archive());
        Consumers.ifNonNull(this.link(), link -> builder.setLink(link.toProto()));
        return builder.build();
    }

}