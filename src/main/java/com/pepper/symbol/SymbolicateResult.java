package com.pepper.symbol;

import java.nio.file.Path;

public class SymbolicateResult {
    private int statusCode;
    private String desc;
    private Path filePath;
    private String fileUrl;

    public SymbolicateResult() {
        this.statusCode = 0;
        this.desc = null;
        this.filePath = null;
        this.fileUrl = null;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public String getFilename() {
        return this.filePath.getFileName().toString();
    }

}