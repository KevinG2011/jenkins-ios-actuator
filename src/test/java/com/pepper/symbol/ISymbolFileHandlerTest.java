package com.pepper.symbol;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ISymbolFileHandlerTest {
    private ISymbolFileHandler fileHandler;
    private String content;

    @Before
    public void init() throws IOException {
        // String pathStr = "/Users/Loriya/Desktop/CrashReport/crashLog/1_parse.crash";
        String pathStr = "/Users/lijia/Desktop/CrashReport/crashLog/1.crash";
        this.content = new String(Files.readAllBytes(Paths.get(pathStr)));
        this.fileHandler = IOSSymbolFileHandler.of(this.content);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInit() {
        assertNotNull(this.fileHandler);
    }

    @Test
    public void testIsVaildContent() throws IOException {
        boolean isValid = IOSSymbolFileHandler.isVaildContent(this.content);
        assertTrue(isValid);
    }

    @Test
    public void testExtractIdentifier() throws IOException {
        String identifier = this.fileHandler.extractIdentifier();
        assertNotNull(identifier);
    }
}