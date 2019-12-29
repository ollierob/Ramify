package net.ramify.model.record.provider;

import com.google.common.collect.Lists;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.image.ImageId;
import net.ramify.model.record.image.RecordImage;
import net.ramify.model.record.image.RecordImages;
import net.ramify.model.record.image.RecordImagesProvider;
import net.ramify.utils.file.FileTraverseUtils;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.File;
import java.net.URISyntaxException;

class DirectoryRecordImagesProvider implements RecordImagesProvider {

    @CheckForNull
    @Override
    public RecordImages get(final RecordSetId id) {
        final var basePath = "/images/records/" + id.value();
        final var dir = this.getClass().getResource(basePath);
        if (dir == null) return null;
        try {
            final var files = Lists.<RecordImage>newArrayList();
            final var baseDir = new File(dir.toURI());
            FileTraverseUtils.traverseSubdirectories(baseDir, this::isImage, file -> files.add(new DirectoryRecordImage(file.getParentFile().getName(), file)));
            return RecordImages.of(basePath, files);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean isImage(final File file) {
        final var name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".tif");
    }

    private static class DirectoryRecordImage implements RecordImage {

        private final ImageId id;
        private final String filename;
        private final String group;

        private DirectoryRecordImage(final String group, final File file) {
            this.group = group;
            final var name = file.getName();
            this.id = new ImageId(name.substring(0, name.indexOf('.')));
            this.filename = Functions.ifNonNull(group, g -> g + "/", "") + name;
        }

        @Nonnull
        @Override
        public ImageId id() {
            return id;
        }

        @CheckForNull
        @Override
        public String group() {
            return group;
        }

        @Nonnull
        @Override
        public String filename() {
            return filename;
        }

        @CheckForNull
        @Override
        public String thumbnailPath() {
            return null;
        }

    }

}
