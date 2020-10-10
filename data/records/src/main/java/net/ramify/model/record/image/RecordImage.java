package net.ramify.model.record.image;

import net.ollie.protobuf.BuildsProto;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.File;

public interface RecordImage extends HasRecordSetId, BuildsProto<RecordProto.RecordImage> {

    @Nonnull
    ImageId id();

    @Nonnull
    File file();

    @Nonnull
    default String path() {
        return this.file().getAbsolutePath();
    }

    @CheckForNull
    String thumbnailPath();

    @Nonnull
    @Override
    default RecordProto.RecordImage toProto() {
        return RecordProto.RecordImage.newBuilder()
                .setId(this.id().value())
                .setGroup(this.recordSetId().value())
                .build();
    }

}
