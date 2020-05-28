package com.pepper.jenkins.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DSymbolControllerTest {
    private DSymbolController fm;

    @Before
    public void init() {
        this.fm = new DSymbolController();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetWorkspaceDirectory() {
        Path dirPath = fm.getWorkspaceDirectory(DSymbolController.SYMBOLIC_DIR);
        assertTrue(Files.exists(dirPath, LinkOption.NOFOLLOW_LINKS));
    }

    @Test
    public void testGetWorkspaceTmpDirectory() {
        Path dirPath = fm.getWorkspaceTmpDirectory(DSymbolController.CRASH_DIR);
        assertTrue(Files.exists(dirPath, LinkOption.NOFOLLOW_LINKS));
    }

    @Test
    public void testGetWorkspacePathSegmentsUrl() {
        String url = fm.getWorkspacePathSegmentsUrl(DSymbolController.SYMBOLIC_DIR);
        assertNotNull(url);
    }

    @Test
    public void testCleanupCrash() throws IOException {
        try {
            Thread t = fm.cleanUp();
            t.join();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testMoveFile() throws IOException {
        String symbolicPathname = "/Users/lijia/Documents/Project/Jenkins/ios-actuator/work/workspace/test@tmp/crash/11.txt";
        Path symbolicPath = Paths.get(symbolicPathname);
        String pathname = "/Users/lijia/Documents/Project/Jenkins/ios-actuator/work/workspace/test/symbolic";
        Path targetPath = Paths.get(pathname).resolve(symbolicPath.getFileName());
        // 移动符号化后的文件到symbol文件夹
        Path newPath = Files.move(symbolicPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        assertTrue(targetPath.compareTo(newPath) == 0);
    }
}