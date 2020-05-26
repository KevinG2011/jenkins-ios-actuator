package com.pepper.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {
    private ZipUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Path unzipDSYM(InputStream is, Path targetPath) throws IOException {
        Path dsymPath = null;
        try (ZipInputStream zipIn = new ZipInputStream(is)) {
            for (ZipEntry ze; (ze = zipIn.getNextEntry()) != null;) {
                String entryName = ze.getName();
                Path resolvedPath = targetPath.resolve(entryName);
                if (ze.isDirectory()) {
                    Files.createDirectories(resolvedPath);
                    if (entryName.endsWith(".dSYM/")) {
                        dsymPath = resolvedPath;
                    }
                } else {
                    Files.createDirectories(resolvedPath.getParent());
                    Files.copy(zipIn, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        return dsymPath;
    }
}