package org.cli.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public void changeDirectory(String pathStr ) throws IOException {
        if (DirectoryChecker.isDirectory(pathStr)) {
            currentDirectory = Paths.get( pathStr );
        } else {
            throw new IOException("Invalid directory");
        }
    }
}
