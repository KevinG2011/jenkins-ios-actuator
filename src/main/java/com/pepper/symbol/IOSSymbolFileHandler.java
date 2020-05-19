package com.pepper.symbol;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOSSymbolFileHandler implements ISymbolFileHandler {
    private String keyword;
    private String regex;
    private File file;
    private CharSequence cs;

    public IOSSymbolFileHandler(File file, String keyword, String regex) {
        this.file = file;
        this.keyword = keyword;
        this.regex = regex;
        this.cs = this.charSequenceOf();
    }

    private CharSequence charSequenceOf() {
        try (FileInputStream fis = new FileInputStream(this.file)) {
            FileChannel fc = fis.getChannel();
            ByteBuffer bbuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int) fc.size());
            CharBuffer cbuf = StandardCharsets.UTF_8.newDecoder().decode(bbuf);
            return cbuf;
        } catch (Exception e) {
            System.err.println("thrown exception: " + e.toString());
        }
        return null;
    }

    @Override
    public boolean hasValidKeyword() throws IOException {
        final Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(cs);
        return matcher.find();
    }

    @Override
    public String extractIdentifier() throws IOException {
        final Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cs);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    @Override
    public File process() {
        // TODO Auto-generated method stub
        return null;
    }
}
