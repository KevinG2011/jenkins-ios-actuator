package com.pepper.symbol;

import org.apache.commons.lang.StringUtils;

public class IOSDSymbolCommand {
    public static final String EXPORT_CMD = "export DEVELOPER_DIR=/Applications/XCode.app/Contents/Developer";
    public static final String BIN = "/Applications/Xcode.app/Contents/SharedFrameworks/DVTFoundation.framework/Versions/A/Resources/symbolicatecrash";
    private String inputName;
    private String dsymName;

    public IOSDSymbolCommand(String inputName, String dsymName) {
        this.inputName = inputName;
        this.dsymName = dsymName;
    }

    public String command() {
        StringBuilder sb = new StringBuilder(EXPORT_CMD);
        sb.append(";");
        String cmd = String.format("%s %s %s", BIN, // bin
                StringUtils.defaultString(inputName), // input file
                StringUtils.defaultString(dsymName)); // DSYM file
        sb.append(cmd);
        return sb.toString();
    }
}