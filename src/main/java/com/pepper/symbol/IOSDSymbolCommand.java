package com.pepper.symbol;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class IOSDSymbolCommand {
    public static final String EXPORT_CMD = "export DEVELOPER_DIR=/Applications/XCode.app/Contents/Developer";
    public static final String BIN = "/Applications/Xcode.app/Contents/SharedFrameworks/DVTFoundation.framework/Versions/A/Resources/symbolicatecrash";

    private IOSDSymbolCommand() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String> commandOf(String inputName, String dsymName) {
        List<String> command = new ArrayList<>(3);
        command.add(BIN);
        command.add(StringUtils.defaultString(inputName));
        command.add(StringUtils.defaultString(dsymName));
        return command;
    }
}