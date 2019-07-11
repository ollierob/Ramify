package net.ramify.utils.file;

import com.google.common.base.Preconditions;

import java.io.File;

public class FileUtils {

    public static void checkReadableFile(final File file) {
        Preconditions.checkState(file.isFile(), "Not a file: %s", file);
        Preconditions.checkState(file.canRead(), "Not a readable file: %s", file);
    }

}
