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
        String zipname = "/Users/Loriya/Downloads/jetbrains-agent.zip";
        String target = "/Users/Loriya/Downloads/test@tmp";
        File file = new File(zipname);
        assertTrue(file.canRead());
        Path targetPath = Paths.get(target);
        try {
            InputStream is = new FileInputStream(file);
            ZipUtils.unzip(is, targetPath);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }

    }
}