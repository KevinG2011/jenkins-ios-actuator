package com.pepper.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
    public void testUnZip() {
        String target = "/Users/lijia/Desktop/CrashReport/build";
        Path targetPath = Paths.get(target);
        Path zipPath = targetPath.resolve("2517-dSYM.zip");
        File file = zipPath.toFile();
        assertTrue(file.canRead());
        try {
            InputStream is = new FileInputStream(file);
            ZipUtils.unzip(is, targetPath);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            fail("Should not have thrown any exception");
        }

    }
}