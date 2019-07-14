package net.ramify.model.record.image;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Objects;

public interface RecordImages extends BuildsProto<RecordProto.RecordImageList> {

    @Nonnull
    String basePath();

    @Nonnull
    Collection<RecordImage> images();

    @Nonnull
    @Override
    default RecordProto.RecordImageList toProto() {
        return RecordProto.RecordImageList.newBuilder()
                .setBasePath(this.basePath())
                .addAllImage(Iterables.transform(this.images(), RecordImage::toProto))
                .build();
    }

    static RecordImages of(final String basePath, final Collection<? extends RecordImage> images) {
        Objects.requireNonNull(basePath);
        final var immutable = ImmutableList.<RecordImage>copyOf(images);
        return new RecordImages() {

            @Nonnull
            @Override
            public String basePath() {
                return basePath;
            }

            @Nonnull
            @Override
            public Collection<RecordImage> images() {
                return immutable;
            }

        };
    }

}
