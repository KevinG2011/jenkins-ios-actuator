package com.pepper.symbol;

import java.io.File;

public class SymbolicateResult {
    private int statusCode;
    private String desc;
    private File file;
    private String fileUrl;

    public SymbolicateResult() {
        this.statusCode = 0;
        this.desc = null;
        this.file = null;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

}