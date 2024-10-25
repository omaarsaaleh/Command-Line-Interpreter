package org.cli.commands;
import org.cli.utils.DirectoryChecker;
import org.cli.utils.FileSystemManager;
import org.cli.utils.pathresolvers.PathResolver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class rmCommand implements Command{
    private PathResolver resolver ;
    public rmCommand(PathResolver resolver){
        this.resolver = resolver ;
    }
    public String execute(String[] args) {
        String pathStr = args[0];
        pathStr = resolver.resolve(pathStr) ;
        if(DirectoryChecker.isDirectory(pathStr,resolver))
        {
            return String.format("rm: cannot remove '%s': Is a directory", pathStr);
        }
        Path file = Paths.get(pathStr);

        if (!Files.exists(file)) {
            return String.format("rm: cannot remove '%s': No such file or directory", pathStr);
        }

        try  {
            Files.delete(file);
            return null;
        }
        catch (SecurityException e){
            return String.format("rm: failed to remove '%s': Operation not permitted", pathStr);
        }
        catch (Exception e) {
            return String.format("rm: failed to remove '%s': %s", pathStr, e.getMessage());
        }


    }

}
