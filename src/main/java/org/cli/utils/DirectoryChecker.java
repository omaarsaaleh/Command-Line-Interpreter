package org.cli.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryChecker {

    public static boolean isDirectory(String pathStr) {
        if(pathStr==null) return false;
        pathStr = PathResolver.resolve(pathStr);
        Path path = Paths.get(pathStr);
        return Files.isDirectory(path);
    }
    public static boolean isParentDirectory(String pathStr ){
        if(pathStr==null) return false;
        pathStr = PathResolver.resolve(pathStr);
        Path parentPath = Paths.get(pathStr).getParent();
        return parentPath != null && Files.isDirectory(parentPath);
    }
}
