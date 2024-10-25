package org.cli.commands;
import org.cli.utils.FileSystemManager;
import org.cli.utils.pathresolvers.*;


public class cdCommand implements Command{
    private PathResolver resolver ;
    public cdCommand(PathResolver resolver){
        this.resolver = resolver ;
    }

    @Override
    public String execute(String[] args) {
        String path = resolver.resolve(args[0]);
        try {
            FileSystemManager.getInstance().changeDirectory(path, resolver);
            return null;
        }
        catch (Exception e) {
            return String.format("cd: %s: No such file or directory", path);
        }
    }
}

