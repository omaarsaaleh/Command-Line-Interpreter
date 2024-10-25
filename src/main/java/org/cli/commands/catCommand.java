package org.cli.commands;

import org.cli.utils.DirectoryChecker;
import org.cli.utils.pathresolvers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class catCommand implements Command{
    private PathResolver resolver ;
    public catCommand(PathResolver resolver){
        this.resolver = resolver ;
    }

    @Override
    public String execute(String[] args) {
        String pathStr = args[0];
        pathStr = resolver.resolve(pathStr) ;

        Path file = Paths.get(pathStr);
        if (!Files.exists(file)) {
            return String.format("cat: '%s': No such file or directory", pathStr);
        }
        if(DirectoryChecker.isDirectory(pathStr,resolver)){
            return String.format("cat: '%s': Is a directory", pathStr);
        }

        try {
            return String.join("\n", Files.readAllLines(Paths.get(pathStr)));
        }
        catch (SecurityException e){
            return String.format("cat: '%s': Permission denied", pathStr);
        }
        catch (Exception e) {
            return String.format("cat: '%s': %s", pathStr, e.getMessage());
        }
    }
}
