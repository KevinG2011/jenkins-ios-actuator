package com.pepper.jenkins.manager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hudson.model.Project;

public class JobDSYMFileManager {
    public static final String WORKSPACE_PATH = "ws";
    public static final String ARCHIVE_PATH = "archive";
    private Project project;

    public String findDsymLink(int versionNum) {
        System.out.println("versionNum :" + versionNum);
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}