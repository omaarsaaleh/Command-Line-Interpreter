package org.cli.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.cli.utils.pathresolvers.*;

public class DirectoryChecker {

    public static boolean isDirectory(String pathStr, PathResolver resolver) {
        if(pathStr==null) return false;
        pathStr = resolver.resolve(pathStr);
        Path path = Paths.get(pathStr);
        return Files.isDirectory(path);
    }
    public static boolean isParentDirectory(String pathStr, PathResolver resolver ){
        if(pathStr==null) return false;
        pathStr = resolver.resolve(pathStr);
        Path parentPath = Paths.get(pathStr).getParent();
        return parentPath != null && Files.isDirectory(parentPath);
    }
}
