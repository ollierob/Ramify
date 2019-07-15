package net.ramify.model.record.image;

import com.google.common.base.MoreObjects;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface RecordImage extends BuildsProto<RecordProto.RecordImage> {

    @Nonnull
    String id();

    @CheckForNull
    String group();

    @Nonnull
    String filename();

    @CheckForNull
    String thumbnailPath();

    @Nonnull
    @Override
    default RecordProto.RecordImage toProto() {
        return RecordProto.RecordImage.newBuilder()
                .setId(this.id())
                .setGroup(MoreObjects.firstNonNull(this.group(), ""))
                .setFilename(this.filename())
                .setThumbnail(MoreObjects.firstNonNull(this.thumbnailPath(), ""))
                .build();
    }

}
