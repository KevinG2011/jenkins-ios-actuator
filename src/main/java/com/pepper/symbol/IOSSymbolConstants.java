package com.pepper.symbol;

public class IOSSymbolConstants {
    public static final String SIGNATURE = "com.huajiao.seeding";
    public static final String REGEX_VERSION = "Version:\\s+\\d{10}";
    public static final String DSYM_FILENAME = "living.app.dSYM";

    private IOSSymbolConstants() {
        throw new IllegalStateException("Utility class");
    }
}