package com.pepper.symbol;

import java.nio.file.Path;

public class SymbolicateResult {
    private int statusCode;
    private String desc;
    private Path dsymbolPath;

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

    public Path getDsymbolPath() {
        return dsymbolPath;
    }

    public void setDsymbolPath(Path dsymbolPath) {
        this.dsymbolPath = dsymbolPath;
    }

}