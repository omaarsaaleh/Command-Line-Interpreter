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
        try {
            String path = LinuxPathResolver.getInstance().resolve(args[0]);
            FileSystemManager.getInstance().changeDirectory(path, resolver);
            return null;
        } catch (Exception e) {
            return "Cannot find path";
        }
    }
}

