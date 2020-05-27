package com.pepper.jenkins;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.pepper.jenkins.manager.DSYMFileManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DSYMFileManagerTest {
    private DSYMFileManager fm;

    @Before
    public void init() {
        this.fm = new DSYMFileManager();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetWorkspaceDirectory() {
        Path dirPath = fm.getWorkspaceDirectory(DSYMFileManager.SYMBOLIC_DIR);
        assertTrue(Files.exists(dirPath, LinkOption.NOFOLLOW_LINKS));
    }

    @Test
    public void testGetWorkspaceTmpDirectory() {
        Path dirPath = fm.getWorkspaceTmpDirectory(DSYMFileManager.CRASH_DIR);
        assertTrue(Files.exists(dirPath, LinkOption.NOFOLLOW_LINKS));
    }

    @Test
    public void testGetWorkspacePathSegmentsUrl() {
        String url = fm.getWorkspacePathSegmentsUrl(DSYMFileManager.SYMBOLIC_DIR);
        assertNotNull(url);
    }

    @Test
    public void testCleanupCrash() throws IOException {
        try {
            Thread t = fm.cleanUpCrashFiles();
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