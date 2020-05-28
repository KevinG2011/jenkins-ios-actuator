package com.pepper.symbol;

import java.nio.file.Path;

public class SymbolicResult {
    private int statusCode;
    private String desc;
    private Path filePath;
    private String fileUrl;
    private String dsymUrl;

    public static SymbolicResult resultOf() {
        return new SymbolicResult();
    }

    private SymbolicResult() {
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

    public String getDsymUrl() {
        return dsymUrl;
    }

    public void setDsymUrl(String dsymUrl) {
        this.dsymUrl = dsymUrl;
    }
}