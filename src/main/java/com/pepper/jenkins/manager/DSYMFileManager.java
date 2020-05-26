package com.pepper.jenkins.manager;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pepper.symbol.IOSSymbolFileHandler;
import com.pepper.symbol.SymbolicateResult;
import com.pepper.utils.ZipUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import hudson.model.Project;

public class DSYMFileManager {
    public static final String WORKSPACE_NAME = "ws";
    public static final String ARCHIVE_NAME = "archive";
    public static final String INPUT_NAME = "input.crash";
    private Project project;

    public Path findDSYMLocalPath(int versionNum) {
        String versionName = String.valueOf(versionNum);
        String fileName = String.format("%d-dSYM.zip", versionNum);
        String workspaceName = this.project.getSomeWorkspace().getRemote();
        Path path = Paths.get(workspaceName, ARCHIVE_NAME, versionName, fileName);
        return path;
    }

    public String findDSYMRemoteUrl(int versionNum) {
        Path path = this.findDSYMLocalPath(versionNum);
        String dsymRemoteUrl = null;
        if (Files.exists(path)) {
            dsymRemoteUrl = String.format("%s/%s/%s/%d/%d-dSYM.zip", this.project.getAbsoluteUrl(), WORKSPACE_NAME,
                    ARCHIVE_NAME, versionNum, versionNum);
        }

        System.out.println("dsymRemoteUrl : " + dsymRemoteUrl);
        return dsymRemoteUrl;
    }

    public SymbolicateResult symbolicate(FileItem fileItem) {
        SymbolicateResult sr = new SymbolicateResult();
        sr.setStatusCode(0);

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
                    sr.setDesc("无效的文件内容");
                    break;
                }
                handler.setOutputDir(workspaceTmp);

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
                Path dsymPath = ZipUtils.unzipDSYM(fis, wsTmpPath);
                if (null == dsymPath) {
                    sr.setDesc("DSYM文件获取失败");
                    break;
                }

                handler.setDsymPathname(dsymPath.toString());
                File dsymbolFile = handler.process();
                sr.setDsymbolFile(dsymbolFile);
            } while (false);
        } catch (Exception e) {
            System.err.println(e);
            sr.setDesc(e.getLocalizedMessage());
            sr.setStatusCode(-1);
        }

        return sr;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}