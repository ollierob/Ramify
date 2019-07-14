package net.ramify.model.record.provider;

import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.image.RecordImage;
import net.ramify.model.record.image.RecordImages;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

class DirectoryRecordImageProvider implements RecordImageProvider {

    @CheckForNull
    @Override
    public RecordImages get(final RecordSetId id) {
        final var basePath = "/images/records/" + id.value();
        final var dir = this.getClass().getResource(basePath);
        if (dir == null) return null;
        try {
            final var file = new File(dir.toURI());
            final var files = Arrays.stream(file.listFiles(f -> f.isFile() && f.canRead() && f.getName().endsWith(".jpg")))
                    .sorted()
                    .map(DirectoryRecordImage::new)
                    .collect(Collectors.toList());
            return RecordImages.of(basePath, files);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static class DirectoryRecordImage implements RecordImage {

        private final String id;
        private final String filename;

        private DirectoryRecordImage(final File file) {
            final var name = file.getName();
            this.id = name.substring(0, name.indexOf('.'));
            this.filename = name;
        }

        @Nonnull
        @Override
        public String id() {
            return id;
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
