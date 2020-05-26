package com.pepper.jenkins;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DSYMFileManagerTest {
    @Before

    public void init() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCleanupCrash() throws IOException {
        String pathname = "/Users/lijia/Documents/Project/Jenkins/ios-actuator/work/workspace/test@tmp/crash";
        Path tmpCrashPath = Paths.get(pathname);
        try (Stream<Path> paths = Files.walk(tmpCrashPath, 1)) {
            paths.filter(path -> {
                return (!path.toString().endsWith("txt"));
            }).forEach(path -> {
                System.out.println(path.toString());
                if (tmpCrashPath.compareTo(path) != 0) {
                    FileUtils.deleteQuietly(path.toFile());
                }
            });
        }
    }

}