package net.ramify.utils.file;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.function.Consumer;

public class FileTraverseUtils {

    private static final File[] EMPTY_FILES = new File[0];

    public static void traverseDirectory(final File directory, final FileFilter filter, final Consumer<File> reader) {
        Preconditions.checkArgument(directory.isDirectory(), "Not a directory: %s", directory);
        Preconditions.checkArgument(directory.canRead(), "Not a readable directory: %s", directory);
        //First read files
        final var files = MoreObjects.firstNonNull(directory.listFiles(filter), EMPTY_FILES);
        Arrays.sort(files);
        for (final var file : files) {
            if (file.isFile()) reader.accept(file);
        }
        //Second read subdirectories
        final var directories = MoreObjects.firstNonNull(directory.listFiles(File::isDirectory), EMPTY_FILES);
        Arrays.sort(directories);
        for (final var subdirectory : directories) {
            traverseDirectory(subdirectory, filter, reader);
        }
    }

}
