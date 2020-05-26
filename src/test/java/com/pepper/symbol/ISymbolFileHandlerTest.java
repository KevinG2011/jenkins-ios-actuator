package com.pepper.symbol;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ISymbolFileHandlerTest {
    private ISymbolFileHandler fileHandler;

    @Before
    public void init() throws IOException {
        // String pathStr = "/Users/Loriya/Desktop/CrashReport/crashLog/1_parse.crash";
        String inputPathname = "/Users/lijia/Desktop/CrashReport/crashLog/1.crash";
        String dsymPathname = "/Users/lijia/Desktop/CrashReport/build/living.app.dSYM";
        File file = new File(inputPathname);
        IOSSymbolFileHandler iosFileHandler = IOSSymbolFileHandler.of(file);
        iosFileHandler.setDsymPathname(dsymPathname);
        this.fileHandler = iosFileHandler;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInit() {
        assertNotNull(this.fileHandler);
    }

    @Test
    public void testExtractIdentifier() throws IOException {
        String identifier = this.fileHandler.extractIdentifier();
        assertNotNull(identifier);
    }

    @Test
    public void testCommandLine() {
        String inputName = "/Users/lijia/Desktop/CrashReport/crashLog/1.crash";
        String dsymName = "/Users/lijia/Desktop/CrashReport/build/living.app.dSYM";
        IOSSymbolCommand cmd = new IOSSymbolCommand(inputName, dsymName);
        String commandLine = cmd.command();
        assertNotNull(commandLine);
    }

    @Test
    public void testProcesser() throws Exception {
        File dsymbolFile = this.fileHandler.process();
        assertTrue(dsymbolFile.exists());
        // this.project.getrooten
    }
}