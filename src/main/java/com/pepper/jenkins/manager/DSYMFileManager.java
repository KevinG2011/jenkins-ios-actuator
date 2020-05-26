package com.pepper.jenkins.manager;

import java.io.File;
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
    public static final String SYMBOL_DIR = "symbol";
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

    private Path getWorkspaceSymbolDirectory() {
        Path wsSymbolPath = null;
        try {
            wsSymbolPath = Paths.get(this.project.getSomeWorkspace().getRemote(), SYMBOL_DIR);
            if (Files.exists(wsSymbolPath, LinkOption.NOFOLLOW_LINKS)
                    && !Files.isDirectory(wsSymbolPath, LinkOption.NOFOLLOW_LINKS)) {
                FileUtils.deleteDirectory(wsSymbolPath.toFile());
            }
            Files.createDirectory(wsSymbolPath);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return wsSymbolPath;
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

    private String getWorkspaceSymbolUrl() {
        String wsUrl = this.getWorkspaceUrl();
        this.getWorkspaceSymbolDirectory();
        String wsSymbolUrl = String.format("%s/%s", wsUrl, SYMBOL_DIR);
        return wsSymbolUrl;
    }

    private String getWorkspaceSymbolFileUrl(String filename) {
        String wsSymbolUrl = this.getWorkspaceSymbolUrl();
        String symbolFileUrl = String.format("%s/%s", wsSymbolUrl, filename);
        System.out.println("symbolFileUrl :" + symbolFileUrl);
        return symbolFileUrl;
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
            File inputFile = inputFilePath.toFile();
            do {
                IOSSymbolFileHandler handler = IOSSymbolFileHandler.of(inputFile);
                if (null == handler) {
                    sr.setDesc("无效的文件内容");
                    break;
                }
                handler.setOutputDir(tmpCrashPath.toString());

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
                handler.setDsymPathname(dsymPath.toString());
                // 解析文件
                File symbolicFile = handler.process();
                // 设置符号化文件
                sr.setFile(symbolicFile);
                // 设置符号化文件Url
                if (symbolicFile != null) {
                    String filename = symbolicFile.getName();
                    Path targetPath = this.getWorkspaceSymbolDirectory().resolve(filename);
                    // 移动符号化后的文件到symbol文件夹
                    Files.move(symbolicFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    sr.setFileUrl(this.getWorkspaceSymbolFileUrl(filename));
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
                stream.filter(path -> {
                    return (!path.toString().endsWith("txt"));
                }).forEach(path -> {
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