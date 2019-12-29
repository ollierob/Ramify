package net.ramify.model.record.image;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collection;

public interface RecordImages extends BuildsProto<RecordProto.RecordImageList> {

    @Nonnull
    Collection<RecordImage> images();

    @CheckForNull
    default RecordImage image(final ImageId imageId) {
        return Iterables.find(this.images(), image -> image.id().equals(imageId), null);
    }

    @Nonnull
    @Override
    default RecordProto.RecordImageList toProto() {
        return RecordProto.RecordImageList.newBuilder()
                .addAllImage(Iterables.transform(this.images(), RecordImage::toProto))
                .build();
    }

    static RecordImages of(final Collection<? extends RecordImage> images) {
        final var immutable = ImmutableList.<RecordImage>copyOf(images);
        return () -> immutable;
    }

}
