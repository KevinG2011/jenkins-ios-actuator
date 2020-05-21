package com.pepper.symbol;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;

public class IOSSymbolFileHandler implements ISymbolFileHandler {

    public static final Pattern pattern = Pattern.compile(IOSSymbolConstants.SIGNATURE);

    private final String content;
    private String identifier;
    private String destPath;

    public static IOSSymbolFileHandler of(FileItem item) {
        final String content = item.getString();
        return IOSSymbolFileHandler.of(content);
    }

    public static IOSSymbolFileHandler of(String content) {
        boolean isVaild = IOSSymbolFileHandler.isVaildContent(content);
        if (isVaild) {
            return new IOSSymbolFileHandler(content);
        }
        return null;
    }

    public static boolean isVaildContent(CharSequence cs) {
        Matcher matcher = pattern.matcher(cs);
        return matcher.find();
    }

    private IOSSymbolFileHandler(String content) {
        this.content = content;
    }

    @Override
    public String extractIdentifier() throws IOException {
        Pattern verPattern = Pattern.compile(IOSSymbolConstants.REGEX_VERSION);
        Matcher matcher = verPattern.matcher(this.content);
        if (matcher.find()) {
            String group = matcher.group(0);
            this.identifier = group.substring(group.length() - 10);
        }
        return this.identifier;
    }

    @Override
    public File process() {
        return null;
    }
}
