package net.ramify.utils.file;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilsTest {

    @Test
    void testSort() {

        final var f1 = new File("a.xml");
        final var f2 = new File("a-2.xml");

        final var files = Lists.newArrayList(f2, f1);
        files.sort(FileUtils::naturalSort);

        assertEquals(Arrays.asList(f1, f2), files);

    }

}