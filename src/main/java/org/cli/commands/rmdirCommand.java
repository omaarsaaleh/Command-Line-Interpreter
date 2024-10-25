package org.cli.commands;
import org.cli.utils.DirectoryChecker;
import org.cli.utils.FileSystemManager;
import org.cli.utils.pathresolvers.PathResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class rmdirCommand implements Command{
    private PathResolver resolver ;
    public rmdirCommand(PathResolver resolver){
        this.resolver = resolver ;
    }

    public String execute(String[] args) {
        String pathStr = args[0];
        if(!DirectoryChecker.isDirectory(pathStr,resolver))
        {
            return String.format("rmdir: failed to remove '%s': Not a directory", pathStr);
        }
        pathStr = resolver.resolve(pathStr) ;
        Path directory = Paths.get(pathStr);

        try  {
            if(Files.list(directory).findAny().isEmpty()){
                Files.delete(directory);
                return null ;
            }
            return String.format("rmdir: failed to remove '%s': Directory not empty", pathStr);
        }
        catch (SecurityException e){
            return String.format("rmdir: failed to remove '%s': Operation not permitted", pathStr);
        }
        catch (Exception e) {
            return String.format("rmdir: failed to remove '%s': %s", pathStr, e.getMessage());
        }

    }
}
