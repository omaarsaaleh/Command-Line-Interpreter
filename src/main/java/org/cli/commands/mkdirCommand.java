package org.cli.commands;
import org.cli.utils.FileSystemManager;
import org.cli.utils.pathresolvers.PathResolver;
import org.cli.utils.DirectoryChecker ;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class mkdirCommand implements Command{
    private PathResolver resolver ;
    public mkdirCommand(PathResolver resolver){
        this.resolver = resolver ;
    }
    public String execute(String[] args) {
        String pathStr = args[0];
        if(!DirectoryChecker.isParentDirectory(pathStr,resolver))
        {
            return String.format("mkdir: cannot create directory ‘%s’: No such file or directory", pathStr);
        }
        pathStr = resolver.resolve(pathStr);

        Path path = Paths.get(pathStr);

        if (Files.exists(path)) {
            return String.format("mkdir: cannot create directory ‘%s’: File exists", pathStr);
        }
        else {
            try {
                Files.createDirectory(path);
                return null;
            }
            catch (IOException e) {
                return  "mkdir: An error occurred while creating the directory: " + e.getMessage() ;
            }
        }
    }
}
