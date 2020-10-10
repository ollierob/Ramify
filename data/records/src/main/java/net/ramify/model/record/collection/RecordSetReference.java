package net.ramify.model.record.collection;

import net.meerkat.functions.consumers.Consumers;
import net.ollie.protobuf.BuildsProto;
import net.ramify.model.record.archive.Archive;
import net.ramify.model.record.archive.HasArchive;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.util.Link;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * @see Archive
 */
public interface RecordSetReference extends HasArchive, BuildsProto<RecordProto.ExternalRecordReference> {

    @Nonnull
    String reference();

    @Nonnull
    Archive archive();

    @CheckForNull
    Link link();

    @Nonnull
    @Override
    default RecordProto.ExternalRecordReference toProto() {
        final var builder = RecordProto.ExternalRecordReference.newBuilder()
                .setReference(this.reference())
                .setArchive(this.archive().toProto());
        Consumers.ifNonNull(this.link(), link -> builder.setLink(link.toProto()));
        return builder.build();
    }

}
