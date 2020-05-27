package com.pepper.jenkins.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import com.pepper.symbol.IOSSymbolFileHandler;
import com.pepper.symbol.SymbolicateResult;
import com.pepper.utils.ZipUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import hudson.model.Project;

public class DSYMFileManager {
    public static final String WORKSPACE_DIR = "ws";
    public static final String ARCHIVE_DIR = "archive";
    public static final String CRASH_DIR = "crash";
    public static final String SYMBOLIC_DIR = "symbolic";
    private Project project;

    private Path getWorkspaceTmpDirectory() {
        Path wsTmpPath = null;
        try {
            String wsTmpPathname = String.format("%s@tmp", this.project.getSomeWorkspace());
            wsTmpPath = Paths.get(wsTmpPathname);
            if (Files.exists(wsTmpPath, LinkOption.NOFOLLOW_LINKS)
                    && !Files.isDirectory(wsTmpPath, LinkOption.NOFOLLOW_LINKS)) {
                FileUtils.deleteDirectory(wsTmpPath.toFile());
            }
            Files.createDirectory(wsTmpPath);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return wsTmpPath;
    }

    private Path getWorkspaceSymbolicDirectory() {
        Path wsSymbolicPath = null;
        try {
            wsSymbolicPath = Paths.get(this.project.getSomeWorkspace().getRemote(), SYMBOLIC_DIR);
            if (Files.exists(wsSymbolicPath, LinkOption.NOFOLLOW_LINKS)
                    && !Files.isDirectory(wsSymbolicPath, LinkOption.NOFOLLOW_LINKS)) {
                FileUtils.deleteDirectory(wsSymbolicPath.toFile());
            }
            Files.createDirectory(wsSymbolicPath);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return wsSymbolicPath;
    }

    private Path getTmpCrashDirectory() {
        Path tmpCrashPath = null;
        try {
            Path wsTmpPath = this.getWorkspaceTmpDirectory();
            tmpCrashPath = wsTmpPath.resolve(CRASH_DIR);
            if (Files.exists(tmpCrashPath, LinkOption.NOFOLLOW_LINKS)
                    && !Files.isDirectory(tmpCrashPath, LinkOption.NOFOLLOW_LINKS)) {
                FileUtils.deleteDirectory(tmpCrashPath.toFile());
            }

            Files.createDirectory(tmpCrashPath);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return tmpCrashPath;
    }

    private String getWorkspaceUrl() {
        String wsUrl = String.format("%s/%s", this.project.getAbsoluteUrl(), WORKSPACE_DIR);
        return wsUrl;
    }

    private String getWorkspaceSymbolicUrl() {
        String wsUrl = this.getWorkspaceUrl();
        this.getWorkspaceSymbolicDirectory();
        String wsSymbolicUrl = String.format("%s/%s", wsUrl, SYMBOLIC_DIR);
        return wsSymbolicUrl;
    }

    private String getWorkspaceSymbolFileUrl(String filename) {
        String wsSymbolicUrl = this.getWorkspaceSymbolicUrl();
        String symbolicFileUrl = String.format("%s/%s", wsSymbolicUrl, filename);
        System.out.println("symbolFileUrl :" + symbolicFileUrl);
        return symbolicFileUrl;
    }

    public Path findDSYMLocalPath(int versionNum) {
        String versionName = String.valueOf(versionNum);
        String fileName = String.format("%d-dSYM.zip", versionNum);
        String workspaceName = this.project.getSomeWorkspace().getRemote();
        Path path = Paths.get(workspaceName, ARCHIVE_DIR, versionName, fileName);
        return path;
    }

    public String findDSYMRemoteUrl(int versionNum) {
        Path path = this.findDSYMLocalPath(versionNum);
        String dsymRemoteUrl = null;
        if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            dsymRemoteUrl = String.format("%s/%s/%s/%d/%d-dSYM.zip", this.project.getAbsoluteUrl(), WORKSPACE_DIR,
                    ARCHIVE_DIR, versionNum, versionNum);
        }

        System.out.println("dsymRemoteUrl : " + dsymRemoteUrl);
        return dsymRemoteUrl;
    }

    public SymbolicateResult symbolicate(FileItem fileItem) {
        SymbolicateResult sr = new SymbolicateResult();
        try {
            Path tmpCrashPath = this.getTmpCrashDirectory();
            Path inputPath = tmpCrashPath.resolve(fileItem.getName());
            Path inputFilePath = Files.write(inputPath, fileItem.get());
            do {
                IOSSymbolFileHandler handler = IOSSymbolFileHandler.of(inputFilePath);
                if (null == handler) {
                    sr.setDesc("无效的文件内容");
                    break;
                }
                handler.setOutputPath(tmpCrashPath);

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
                Path dsymLocalPath = this.findDSYMLocalPath(Integer.parseInt(versionNum));
                if (Files.notExists(dsymLocalPath, LinkOption.NOFOLLOW_LINKS)) {
                    String notFoundStr = String.format("无法查找到[%s]对应的DSYM文件", versionNum);
                    sr.setDesc(notFoundStr);
                    break;
                }
                // 解压到指定目录
                FileInputStream fis = new FileInputStream(dsymLocalPath.toFile());
                Path dsymPath = ZipUtils.unzipDSYM(fis, tmpCrashPath);
                if (null == dsymPath) {
                    sr.setDesc("DSYM文件获取失败");
                    break;
                }
                // 设置DSYM路径
                handler.setDsymPath(dsymPath);
                // 解析文件
                Path symbolicPath = handler.process();
                // 设置符号化文件Url
                if (symbolicPath != null) {
                    Path filenamePath = symbolicPath.getFileName();
                    Path targetPath = this.getWorkspaceSymbolicDirectory().resolve(filenamePath);
                    // 移动符号化后的文件到symbol文件夹
                    Files.move(symbolicPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    // 设置符号化文件
                    sr.setFilePath(targetPath);
                    // 设置符号化文件Url
                    sr.setFileUrl(this.getWorkspaceSymbolFileUrl(filenamePath.toString()));
                }
            } while (false);
        } catch (Exception e) {
            System.err.println(e);
            sr.setDesc(e.getLocalizedMessage());
            sr.setStatusCode(-1);
        }

        return sr;
    }

    public void cleanUpCrashFiles() {
        new Thread(() -> {
            Stream<Path> stream = null;
            try {
                Path tmpCrashPath = this.getTmpCrashDirectory();
                stream = Files.walk(tmpCrashPath, 1);
                stream.filter(path -> (!path.toString().endsWith("txt"))).forEach(path -> {
                    if (tmpCrashPath.compareTo(path) != 0) {
                        FileUtils.deleteQuietly(path.toFile());
                    }
                });
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
            } finally {
                if (stream != null) {
                    stream.close();
                    stream = null;
                }
            }

        }).start();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}