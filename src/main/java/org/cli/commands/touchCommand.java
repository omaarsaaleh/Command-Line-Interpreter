package org.cli.commands;
import org.cli.utils.DirectoryChecker;
import org.cli.utils.FileSystemManager;
import org.cli.utils.pathresolvers.PathResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class touchCommand implements Command {
    private PathResolver resolver ;
    public touchCommand(PathResolver resolver){
        this.resolver = resolver ;
    }

    public String execute(String[] args) {
        String pathStr = args[0];
        if(!DirectoryChecker.isParentDirectory(pathStr,resolver))
        {
            return String.format("touch: cannot touch '%s: No such file or directory", pathStr);
        }
        pathStr = resolver.resolve(pathStr);
        Path path = Paths.get(pathStr);

        if (Files.exists(path)) {
            try{
                FileTime fileTime = FileTime.fromMillis(System.currentTimeMillis());
                Files.setLastModifiedTime(path, fileTime);
                Files.setAttribute(path, "lastAccessTime", fileTime);
                return null ;
            }
            catch (IOException e) {
                return  "touch: An error occurred while creating the directory: " + e.getMessage() ;
            }
        }
        else {
            try {
                Files.createFile(path);
                return null;
            }
            catch (IOException e) {
                return  "touch: An error occurred while creating the directory: " + e.getMessage() ;
            }
        }

    }
}