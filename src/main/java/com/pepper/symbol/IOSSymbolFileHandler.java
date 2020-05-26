package com.pepper.symbol;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

public class IOSSymbolFileHandler implements ISymbolFileHandler {

    public static final Pattern pattern = Pattern.compile(IOSSymbolConstants.SIGNATURE);

    private File file;
    private String fileContent;
    private String identifier;
    private String outputDir;
    private String outputFileName;

    public static boolean isVaildContent(CharSequence cs) {
        Matcher matcher = pattern.matcher(cs);
        return matcher.find();
    }

    public static IOSSymbolFileHandler of(File file) throws UnsupportedEncodingException, IOException {
        Path path = Paths.get(file.getAbsolutePath());
        String fileContent = new String(Files.readAllBytes(path), "utf-8");
        boolean isVaild = IOSSymbolFileHandler.isVaildContent(fileContent);
        if (isVaild) {
            return new IOSSymbolFileHandler(file, fileContent);
        }

        return null;
    }

    private IOSSymbolFileHandler(File file, String fileContent) {
        this.file = file;
        this.fileContent = fileContent;
    }

    @Override
    public String extractIdentifier() throws IOException {
        Pattern verPattern = Pattern.compile(IOSSymbolConstants.REGEX_VERSION);
        Matcher matcher = verPattern.matcher(this.fileContent);
        if (matcher.find()) {
            String group = matcher.group(0);
            this.identifier = group.substring(group.length() - 10);
        }
        return this.identifier;
    }

    @Override
    public File process() throws IOException {
        List<String> commandLine = Lists.newArrayList("bash", "-c", "ls ~/Downloads/");
        ProcessBuilder pb = new ProcessBuilder(commandLine);
        File outputFile = new File(this.outputDir);
        pb.directory(outputFile);
        pb.redirectErrorStream(true);
        String outputPathname = Paths.get(outputDir, this.outputFileName).toString();
        File output = new File(outputPathname);
        pb.redirectOutput(Redirect.to(output));
        pb.start();
        return pb.redirectOutput().file();
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
}
