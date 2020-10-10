package net.ramify.model.record.image;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collection;

public interface RecordImages extends BuildsProto<RecordProto.RecordImageList> {

    @Nonnull
    Collection<RecordImage> images();

    default boolean isEmpty() {
        return this.images().isEmpty();
    }

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

    default RecordImages union(final RecordImages that) {
        if (that == null || that.isEmpty()) return this;
        final var copy = ImmutableSet.<RecordImage>builder()
                .addAll(this.images())
                .addAll(that.images())
                .build();
        return () -> copy;
    }

    static RecordImages of(final Collection<? extends RecordImage> images) {
        final var immutable = ImmutableList.<RecordImage>copyOf(images);
        return () -> immutable;
    }

    RecordImages NONE = ImmutableSet::of;

}
