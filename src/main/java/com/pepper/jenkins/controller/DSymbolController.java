package com.pepper.jenkins.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.pepper.symbol.IOSSymbolFileHandler;
import com.pepper.symbol.SymbolicResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import hudson.model.Project;

public class DSymbolController {
    public static final String WORKSPACE_DIR = "ws";
    public static final String ARCHIVE_DIR = "archive";
    public static final String CRASH_DIR = "crash";
    public static final String SYMBOLIC_DIR = "symbolic";
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Path getWorkspaceDirectory(String... subPaths) {
        Path dirPath = null;
        try {
            String wsPathname = "";
            if (this.project != null) {
                wsPathname = this.project.getSomeWorkspace().getRemote();
            }
            dirPath = Paths.get(wsPathname, subPaths);
            String filename = dirPath.getFileName().toString();
            if (StringUtils.isBlank(FilenameUtils.getExtension(filename))) {
                Files.createDirectories(dirPath);
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return dirPath;
    }

    public Path getWorkspaceTmpDirectory(String... subPaths) {
        Path dirPath = null;
        try {
            Path wsPath = this.getWorkspaceDirectory();
            String wsTmpPathname = wsPath.toString() + "@tmp";
            dirPath = Paths.get(wsTmpPathname, subPaths);

            String filename = dirPath.getFileName().toString();
            if (StringUtils.isBlank(FilenameUtils.getExtension(filename))) {
                Files.createDirectories(dirPath);
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return dirPath;
    }

    public String getWorkspacePathSegmentsUrl(String... args) {
        String wsUrl = this.project.getAbsoluteUrl();
        // String wsUrl = "http://localhost:8080/jenkins/test";
        try {
            URIBuilder builder = new URIBuilder(wsUrl);
            List<String> paths = builder.getPathSegments();
            paths.add(WORKSPACE_DIR);
            List<String> segments = Arrays.asList(args);
            paths.addAll(segments);
            builder.setPathSegments(paths);
            wsUrl = builder.build().toString();
        } catch (URISyntaxException e) {
            System.err.println(e.getLocalizedMessage());
            wsUrl = String.format("%s/%s", this.project.getAbsoluteUrl(), WORKSPACE_DIR);
        }
        return wsUrl;
    }

    public Path findDSYMPath(int versionNum) {
        String versionStr = String.valueOf(versionNum);
        String fileName = String.format("%d-dSYM.zip", versionNum);
        Path filePath = this.getWorkspaceDirectory(ARCHIVE_DIR, versionStr, fileName);
        return filePath;
    }

    public SymbolicResult findDSYMRemoteUrl(int versionNum) {
        SymbolicResult sr = SymbolicResult.resultOf();
        Path path = this.findDSYMPath(versionNum);
        String versionStr = String.valueOf(versionNum);
        if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            String dsymFilename = String.format("%s-dSYM.zip", versionStr);
            String dsymUrl = this.getWorkspacePathSegmentsUrl(ARCHIVE_DIR, versionStr, dsymFilename);
            sr.setDsymUrl(dsymUrl);
        }

        System.out.println("dsymUrl : " + sr.getDsymUrl());
        return sr;
    }

    public SymbolicResult symbolicate(FileItem fileItem) {
        SymbolicResult sr = SymbolicResult.resultOf();
        try {
            do {
                if (fileItem == null || StringUtils.isBlank(fileItem.getName())) {
                    sr.setDesc("请上传文件");
                    break;
                }
                Path tmpCrashPath = this.getWorkspaceTmpDirectory(CRASH_DIR);
                Path inputPath = tmpCrashPath.resolve(fileItem.getName());
                Path inputFilePath = Files.write(inputPath, fileItem.get());
                IOSSymbolFileHandler handler = IOSSymbolFileHandler.of(inputFilePath);
                if (null == handler) {
                    sr.setDesc("无效的文件内容");
                    break;
                }

                String versionNum = handler.extractIdentifier();
                if (StringUtils.isBlank(versionNum)) {
                    sr.setDesc("空的版本号");
                    break;
                }
                if (!StringUtils.isNumeric(versionNum)) {
                    sr.setDesc("非法的版本号");
                    break;
                }
                // 查找本地DSYM文件路径
                Path dsymPath = this.findDSYMPath(Integer.parseInt(versionNum));
                if (Files.notExists(dsymPath, LinkOption.NOFOLLOW_LINKS)) {
                    String notFoundStr = String.format("无法查找到[%s]对应的DSYM文件", versionNum);
                    sr.setDesc(notFoundStr);
                    break;
                }
                System.out.println("dsymPath :" + dsymPath.toString());

                Path outputPath = this.getWorkspaceDirectory(SYMBOLIC_DIR);
                // 设置DSYM路径
                handler.setDsymPath(dsymPath);
                // 设置输出目录
                handler.setOutputPath(outputPath);
                // 解析文件
                Path symbolicPath = handler.process();
                // 设置符号化文件Url
                if (symbolicPath != null) {
                    // 设置符号化文件
                    sr.setFilePath(symbolicPath);
                    // 设置符号化文件Url
                    String fileUrl = this.getWorkspacePathSegmentsUrl(SYMBOLIC_DIR,
                            symbolicPath.getFileName().toString());
                    sr.setFileUrl(fileUrl);
                    this.cleanUp();
                }
            } while (false);
        } catch (Exception e) {
            System.err.println(e);
            sr.setDesc(e.getLocalizedMessage());
            sr.setStatusCode(-1);
        }

        return sr;
    }

    public Thread cleanUp() {
        Thread t = new Thread(() -> {
            Path tmpCrashPath = this.getWorkspaceTmpDirectory(CRASH_DIR);
            try (Stream<Path> stream = Files.walk(tmpCrashPath, 1)) {
                stream.forEach(path -> {
                    if (tmpCrashPath.compareTo(path) != 0) {
                        FileUtils.deleteQuietly(path.toFile());
                    }
                });
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }
        });
        t.start();
        return t;
    }

}