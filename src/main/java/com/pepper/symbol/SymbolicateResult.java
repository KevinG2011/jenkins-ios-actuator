package com.pepper.symbol;

import java.io.File;

public class SymbolicateResult {
    private int statusCode;
    private String desc;
    private File dsymbolFile;

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

    public File getDsymbolFile() {
        return dsymbolFile;
    }

    public void setDsymbolFile(File dsymbolFile) {
        this.dsymbolFile = dsymbolFile;
    }

}