package com.pepper.jenkins.manager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pepper.symbol.IOSSymbolFileHandler;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import hudson.model.Project;

public class DSYMFileManager {
    public static final String WORKSPACE_PATH = "ws";
    public static final String ARCHIVE_PATH = "archive";
    public static final String INPUT_NAME = "input.crash";
    public static final String OUTPUT_NAME = "output.txt";
    private Project project;

    public String findDsymLink(int versionNum) {
        String filePath = String.format("%s/%s/%d/%d-dSYM.zip", this.project.getSomeWorkspace(), ARCHIVE_PATH,
                versionNum, versionNum);
        Path path = Paths.get(filePath);
        String dsymLink = null;
        if (Files.exists(path)) {
            dsymLink = String.format("%s/%s/%s/%d/%d-dSYM.zip", this.project.getAbsoluteUrl(), WORKSPACE_PATH,
                    ARCHIVE_PATH, versionNum, versionNum);
        }

        System.out.println("dsymLink : " + dsymLink);
        return dsymLink;
    }

    public File symbolicate(FileItem fileItem) {
        String workspaceTmp = String.format("%s@tmp", this.project.getSomeWorkspace());
        Path wsTmpPath = Paths.get(workspaceTmp);
        try {
            if (!Files.isDirectory(wsTmpPath, LinkOption.NOFOLLOW_LINKS)) {
                Files.deleteIfExists(wsTmpPath);
                Files.createDirectory(wsTmpPath);
            }

            Path inputPath = Paths.get(workspaceTmp, INPUT_NAME);
            Path inputFilePath = Files.write(inputPath, fileItem.get());
            File inputFile = inputFilePath.toFile();
            do {
                IOSSymbolFileHandler handler = IOSSymbolFileHandler.of(inputFile);
                if (null == handler) {
                    break;
                }
                handler.setOutputDir(workspaceTmp);
                handler.setOutputFileName(OUTPUT_NAME);

                String versionNum = handler.extractIdentifier();
                if (!StringUtils.isNumeric(versionNum)) {
                    break;
                }
                String dsymPathname = this.findDsymLink(Integer.parseInt(versionNum));
                if (null == dsymPathname) {
                    break;
                }
                Path dsymPath = Paths.get(dsymPathname);
                Path targetPath = wsTmpPath.resolve(dsymPath.getFileName());
                if (Files.exists(dsymPath) && Files.isReadable(dsymPath)) {
                    // 解压到目标路径下

                }

                return handler.process();
            } while (false);
        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}