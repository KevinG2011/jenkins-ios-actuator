package com.pepper.symbol;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ISymbolFileHandlerTest {
    private ISymbolFileHandler fileHandler;

    @Before
    public void init() {
        File file = new File("/Users/Loriya/Desktop/CrashReport/crashLog/1_parse.crash");
        String keyword = "com.huajiao.seeding";
        String regex = "build_version\":\"\\d{10}";
        fileHandler = new IOSSymbolFileHandler(file, keyword, regex);
    }

    @Test
    public void testHasValidKeyword() throws IOException {
        boolean isValid = this.fileHandler.hasValidKeyword();
        assertTrue(isValid);
    }

    @Test
    public void testExtractIdentifier() throws IOException {
        String content = this.fileHandler.extractIdentifier();
        String identifier = null;
        if (null != content) {
            identifier = content.substring(content.length() - 10);
        }
        assertNotNull(identifier);
    }
}