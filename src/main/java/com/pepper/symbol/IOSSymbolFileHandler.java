package com.pepper.symbol;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

public class IOSSymbolFileHandler implements ISymbolFileHandler {

    public static final Pattern pattern = Pattern.compile(IOSSymbolConstants.SIGNATURE);

    private String identifier;
    private String fileContent;

    private Path inputPath;
    private Path outputPath;
    private Path dsymPath;

    public static boolean isVaildContent(CharSequence cs) {
        Matcher matcher = pattern.matcher(cs);
        return matcher.find();
    }

    public static IOSSymbolFileHandler of(Path inputPath) throws IOException {
        String fileContent = new String(Files.readAllBytes(inputPath), "utf-8");
        boolean isVaild = IOSSymbolFileHandler.isVaildContent(fileContent);
        if (isVaild) {
            return new IOSSymbolFileHandler(inputPath, fileContent);
        }

        return null;
    }

    private IOSSymbolFileHandler(Path inputPath, String fileContent) {
        this.inputPath = inputPath;
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
    public Path process() throws IOException, InterruptedException {
        String inputPathname = this.inputPath.toString();
        IOSDSymbolCommand cmd = new IOSDSymbolCommand(inputPathname, this.dsymPath.toString());
        List<String> commandLine = Lists.newArrayList("bash", "-c", cmd.command());
        ProcessBuilder pb = new ProcessBuilder(commandLine);
        // pb.directory(this.getOutputPath().toFile());
        pb.redirectErrorStream(true);

        String outputFilename = this.inputPath.getFileName().toString() + ".txt";
        Path outputFilePath = this.getOutputPath().resolve(outputFilename);
        pb.redirectOutput(Redirect.to(outputFilePath.toFile()));
        Process process = null;
        Path symbolicPath = null;
        try {
            process = pb.start();
            process.waitFor();
            String command = pb.command().toString();
            System.out.println("command :" + command);
            File symbolicFile = pb.redirectOutput().file();
            if (symbolicFile != null) {
                symbolicPath = symbolicFile.toPath();
            }
        } finally {
            if (null != process) {
                process.destroy();
                process = null;
            }
        }
        return symbolicPath;
    }

    public Path getOutputPath() {
        if (null == this.outputPath) {
            this.outputPath = this.inputPath.getParent();
        }
        return this.outputPath;
    }

    public void setOutputPath(Path outputPath) {
        this.outputPath = outputPath;
    }

    public void setDsymPath(Path dsymPath) {
        this.dsymPath = dsymPath;
    }

}
