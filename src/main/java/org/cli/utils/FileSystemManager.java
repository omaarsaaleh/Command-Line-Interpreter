package org.cli.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.cli.utils.DirectoryChecker ;
import org.cli.utils.pathresolvers.*;

public class FileSystemManager {
    private static FileSystemManager instance;
    private Path currentDirectory;

    private FileSystemManager() {
        this.currentDirectory = Paths.get(getHomeDirectory());
    }

    public static FileSystemManager getInstance() {
        if (instance == null) {
            instance = new FileSystemManager();
        }
        return instance;
    }

    public String getCurrentDirectory() {
        return currentDirectory.toString();
    }

    public String getHomeDirectory(){
        return System.getProperty("user.home") + "/" ;
    }

    public void changeDirectory(String pathStr, PathResolver resolver) throws IOException {
        if (DirectoryChecker.isDirectory(pathStr, resolver)) {
            currentDirectory = Paths.get(resolver.resolve(pathStr));
        } else {
            throw new IOException("Invalid directory");
        }
    }
}
