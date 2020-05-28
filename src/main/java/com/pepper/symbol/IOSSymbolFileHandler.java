package com.pepper.symbol;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

import org.apache.commons.lang.StringUtils;

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
        Path dsymFilePath = this.dsymPath.resolveSibling(IOSSymbolConstants.DSYM_FILENAME);
        StringBuilder cmdBuilder = new StringBuilder();
        if (Files.notExists(dsymFilePath, LinkOption.NOFOLLOW_LINKS)) {
            // 文件不存在解压
            String unzipCMD = String.format("unzip -q -n %s && ", this.dsymPath.getFileName());
            cmdBuilder.append(unzipCMD);
        }
        // 解析
        Path symbolicPath = null;
        String inputPathname = this.inputPath.toString();
        String dsymFilename = dsymFilePath.getFileName().toString();
        String symbolCMD = String.format("%s %s -d %s", IOSDSymbolCommand.BIN, // bin
                StringUtils.defaultString(inputPathname), // inputname
                StringUtils.defaultString(dsymFilename) // dSYM
        );
        cmdBuilder.append(symbolCMD);

        List<String> commandLine = Lists.newArrayList("bash", "-c", cmdBuilder.toString());
        ProcessBuilder pb = new ProcessBuilder(commandLine);
        String command = pb.command().toString();
        System.out.println("cmd :" + command);
        Map<String, String> env = pb.environment();
        env.put("DEVELOPER_DIR", "/Applications/XCode.app/Contents/Developer");
        pb.directory(this.dsymPath.getParent().toFile());
        pb.redirectErrorStream(true);

        String outputFilename = this.inputPath.getFileName().toString() + ".txt";
        Path outputFilePath = this.getOutputPath().resolve(outputFilename);
        pb.redirectOutput(outputFilePath.toFile());

        Process process = pb.start();
        try {
            File symbolicFile = pb.redirectOutput().file();
            process.waitFor();
            if (symbolicFile != null) {
                symbolicPath = symbolicFile.toPath();
            }
        } finally {
            process.destroy();
            process = null;
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
