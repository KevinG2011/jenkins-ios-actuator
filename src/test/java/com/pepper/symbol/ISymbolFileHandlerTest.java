package com.pepper.symbol;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ISymbolFileHandlerTest {
    private ISymbolFileHandler fileHandler;

    @Before
    public void init() throws IOException {
        // String pathStr = "/Users/Loriya/Desktop/CrashReport/crashLog/1_parse.crash";
        // String inputPathname = "/Users/lijia/Desktop/CrashReport/crashLog/1.crash";
        // String dsymPathname =
        // "/Users/lijia/Desktop/CrashReport/build/living.app.dSYM";
        // IOSSymbolFileHandler iosFileHandler =
        // IOSSymbolFileHandler.of(Paths.get(inputPathname));
        // iosFileHandler.setDsymPath(Paths.get(dsymPathname));
        // this.fileHandler = iosFileHandler;
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
        List<String> command = IOSDSymbolCommand.commandOf(inputName, dsymName);
        assertFalse(command.isEmpty());
    }

    @Test
    public void testProcesser() throws Exception {
        Path symbolicPath = this.fileHandler.process();
        assertTrue(Files.exists(symbolicPath));
        // this.project.getrooten
    }

    public static void main(String[] args) {
        try {
            Path inputPath = Paths.get("first");
            Path outputPath = Paths.get("first");
            Path dsymFilePath = Paths.get("");
            String inputPathname = inputPath.toString();
            String dsymPathname = dsymFilePath.toString();
            List<String> commandLine = IOSDSymbolCommand.commandOf(inputPathname, dsymPathname);
            ProcessBuilder pb = new ProcessBuilder(commandLine);
            Map<String, String> env = pb.environment();
            env.put("DEVELOPER_DIR", "/Applications/XCode.app/Contents/Developer");
            // pb.directory();
            pb.redirectErrorStream(true);
            String outputFilename = inputPath.getFileName().toString() + ".txt";
            Path outputFilePath = outputPath.resolve(outputFilename);
            pb.redirectOutput(outputFilePath.toFile());

            Process p = pb.start();
            int exitCode = p.waitFor();
            p.destroy();
            // final BufferedReader reader = new BufferedReader(new
            // InputStreamReader(p.getInputStream()));

            // StringJoiner sj = new StringJoiner(System.getProperty("line.separator"));
            // reader.lines().iterator().forEachRemaining(sj::add);
            // String result = sj.toString();
            // blocked :(
            System.out.println("\nExited with error code : " + exitCode);
        } catch (Exception e) {
            fail();
        }
    }
}