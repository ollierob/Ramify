package net.ramify.utils.file;

import com.google.common.base.Preconditions;

import java.io.File;

public class FileUtils {

    public static void checkReadableFile(final File file) {
        Preconditions.checkState(file.isFile(), "Not a file: %s", file);
        Preconditions.checkState(file.canRead(), "Not a readable file: %s", file);
    }

    public static int naturalSort(final File f1, final File f2) {
        final var n1 = nameWithoutExtension(f1);
        final var n2 = nameWithoutExtension(f2);
        final int length = Math.min(n1.length(), n2.length());
        final var s1 = n1.substring(0, length);
        final var s2 = n2.substring(0, length);
        final int c = s1.compareTo(s2);
        if (c != 0) return c;
        return n1.length() - n2.length();
    }

    public static String nameWithoutExtension(final File file) {
        final var name = file.getName();
        final var dot = name.lastIndexOf('.');
        return dot < 0 ? name : name.substring(0, dot);
    }

}
