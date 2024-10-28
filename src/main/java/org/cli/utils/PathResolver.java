package org.cli.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathResolver {

    public static String resolve(String path) {
        Path resolvedPath;

        if(System.getProperty("os.name").startsWith("Windows") && path.endsWith(":") ){
            path = path + "\\"  ;
        }

        if (path.startsWith("~")) {
            resolvedPath = Paths.get(FileSystemManager.getInstance().getHomeDirectory(), path.substring(1));
        }
        else {
            Path basePath = Paths.get(path);
            if (!basePath.isAbsolute()) {
                Path currentDir = Paths.get(FileSystemManager.getInstance().getCurrentDirectory());
                resolvedPath = currentDir.resolve(basePath);
            }
            else {
                resolvedPath = basePath;
            }
        }



        return resolvedPath.normalize().toString();
    }


}
