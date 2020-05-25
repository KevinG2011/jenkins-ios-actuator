package com.pepper.utils;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ActuatorDiskUtils {

    private ActuatorDiskUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getFreeDiskSpaceDetail() {
        Path p = Paths.get("/");
        long fsByte;
        try {
            FileStore store = Files.getFileStore(p);
            fsByte = store.getUsableSpace();
        } catch (IOException e) {
            fsByte = 0;
            System.out.println(e);
        }
        double fsG = fsByte / (1024.0 * 1024 * 1024);
        String freeSpaceDetail = "";
        if (fsG < 1.0) {
            double fsMB = fsByte / (1024.0 * 1024);
            freeSpaceDetail = String.format("%.2f MB", fsMB);
        } else {
            freeSpaceDetail = String.format("%.2f G", fsG);
        }
        return freeSpaceDetail;
    }
}
