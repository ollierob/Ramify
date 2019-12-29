package net.ramify.model.record.provider;

import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.image.ImageId;
import net.ramify.model.record.image.RecordImage;
import net.ramify.model.record.image.RecordImages;
import net.ramify.model.record.image.RecordImagesProvider;
import net.ramify.utils.file.FileTraverseUtils;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Singleton
class DirectoryRecordImagesProvider implements RecordImagesProvider {

    private static final String ROOT_PATH = "/images/records/";
    private final Map<RecordSetId, Optional<RecordImages>> images = Maps.newConcurrentMap();
    private final File baseDirectory;

    DirectoryRecordImagesProvider() {
        try {
            final var dir = this.getClass().getResource(ROOT_PATH);
            this.baseDirectory = new File(dir.toURI());
        } catch (URISyntaxException e) {
            throw new Error(e);
        }
    }

    @Override
    public RecordImages get(final RecordSetId id) {
        return images.computeIfAbsent(id, i -> this.find(i.value())).orElse(null);
    }

    private Optional<RecordImages> find(final String name) {
        return FileTraverseUtils.findSubdirectory(baseDirectory, name)
                .map(LazyDirectoryRecordImages::new);
    }

    private static final class LazyDirectoryRecordImages implements RecordImages {

        private final File directory;

        private LazyDirectoryRecordImages(final File directory) {
            this.directory = directory;
        }

        @Nonnull
        @Override
        public Collection<RecordImage> images() {
            final var files = directory.listFiles(this::isImage);
            return Collections2.transform(Arrays.asList(files), DirectoryRecordImage::new);
        }

        private boolean isImage(final File file) {
            final var name = file.getName().toLowerCase();
            return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".tif");
        }

    }

    private static final class DirectoryRecordImage implements RecordImage {

        private final ImageId id;
        private final File file;
        private final RecordSetId recordSetId;

        private DirectoryRecordImage(final File file) {
            this.file = file;
            this.recordSetId = new RecordSetId(file.getParentFile().getName());
            final var name = file.getName();
            this.id = new ImageId(name.substring(0, name.indexOf('.')));
        }

        @Nonnull
        @Override
        public ImageId id() {
            return id;
        }

        @Nonnull
        @Override
        public File file() {
            return file;
        }

        @Nonnull
        @Override
        public String path() {
            final var path = file.getAbsolutePath();
            return path.substring(path.indexOf(ROOT_PATH));
        }

        @Nonnull
        @Override
        public RecordSetId recordSetId() {
            return recordSetId;
        }

        @CheckForNull
        @Override
        public String thumbnailPath() {
            return null;
        }

    }

}
