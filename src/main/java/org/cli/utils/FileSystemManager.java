package org.cli.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemManager {
    private static FileSystemManager instance;
    private Path currentDirectory;

    private FileSystemManager() {
        this.currentDirectory = Paths.get(System.getProperty("user.dir"));
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

    public void changeDirectory(String path) throws IOException {
        Path newPath = currentDirectory.resolve(path).normalize();
        if (Files.isDirectory(newPath)) {
            currentDirectory = newPath;
        } else {
            throw new IOException("Invalid directory");
        }
    }
}
