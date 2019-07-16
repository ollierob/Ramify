package net.ramify.utils.file;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.function.Consumer;

public class FileTraverseUtils {

    private static final File[] EMPTY_FILES = new File[0];

    public static void traverseSubdirectories(final File directory, final FileFilter filter, final Consumer<File> reader) {
        traverseSubdirectories(directory, filter, reader, Integer.MAX_VALUE);
    }

    public static void traverseSubdirectories(final File directory, final FileFilter filter, final Consumer<File> reader, final int maxDepth) {
        Preconditions.checkArgument(directory.isDirectory(), "Not a directory: %s", directory);
        Preconditions.checkArgument(directory.canRead(), "Not a readable directory: %s", directory);
        //First read files
        final var files = MoreObjects.firstNonNull(directory.listFiles(filter), EMPTY_FILES);
        Arrays.sort(files);
        for (final var file : files) {
            if (file.isFile()) reader.accept(file);
        }
        if (maxDepth <= 0) return;
        //Second read subdirectories
        final var directories = MoreObjects.firstNonNull(directory.listFiles(File::isDirectory), EMPTY_FILES);
        Arrays.sort(directories);
        for (final var subdirectory : directories) {
            traverseSubdirectories(subdirectory, filter, reader);
        }
    }

    public static void readSubdirectories(final File directory, final Consumer<File> reader) {
        Preconditions.checkState(directory.isDirectory(), "Not a directory: %s", directory);
        for (final File file : directory.listFiles(File::isDirectory)) {
            reader.accept(file);
        }
    }

    public static void readFilesInDirectory(final File directory, final Consumer<File> reader) {
        Preconditions.checkState(directory.isDirectory(), "Not a directory: %s", directory);
        for (final File file : directory.listFiles(File::isFile)) {
            reader.accept(file);
        }
    }

}
