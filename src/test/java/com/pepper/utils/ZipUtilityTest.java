package com.pepper.utils;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZipUtilityTest {
    @Before
    public void init() {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUnZip() throws IOException {
        String target = "/Users/lijia/Desktop/CrashReport/build";
        Path targetPath = Paths.get(target);
        Path zipPath = targetPath.resolve("2517-dSYM.zip");
        File file = zipPath.toFile();
        assertTrue(file.canRead());
        InputStream is = new FileInputStream(file);
        Path unzipPath = ZipUtils.unzipDSYM(is, targetPath);
        assertTrue(Files.exists(unzipPath));
    }
}