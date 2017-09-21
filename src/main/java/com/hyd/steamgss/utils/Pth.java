package com.hyd.steamgss.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author yiding_he
 */
public class Pth {

    public static boolean fileExists(String path) {
        if (Str.isBlank(path)) {
            return false;
        }
        return Files.exists(Paths.get(path));
    }

    public static boolean getOrCreateDir(String path) {
        if (Str.isBlank(path)) {
            return false;
        }
        if (fileExists(path)) {
            return true;
        } else {
            try {
                Files.createDirectories(Paths.get(path));
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    public static void copyDir(String srcPath, String dstPath) throws IOException {
        Files.walkFileTree(Paths.get(srcPath), new CopyDir(Paths.get(srcPath), Paths.get(dstPath)));
    }
}
