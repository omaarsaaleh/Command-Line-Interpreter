package org.cli.commands;

import org.cli.utils.DirectoryChecker;
import org.cli.utils.pathresolvers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class mvCommand implements Command{
    // mv [source_file_name(s)] [Destination_file_name]
    private PathResolver resolver ;
    public mvCommand(PathResolver resolver){
        this.resolver = resolver ;
    }
    @Override
    public String execute(String[] args) {
        String source = resolver.resolve(args[0]);
        String destination = resolver.resolve(args[1]);

        Path sourceFile = Paths.get(source);
        Path destDir = Paths.get(destination);

        if(!Files.exists(sourceFile)){
            return String.format("mv: cannot stat '%s': No such file or directory", sourceFile);
        }
        if(!DirectoryChecker.isParentDirectory(destination,resolver)){
            return String.format("mv: cannot move '%s' to '%s': No such file or directory",source,destination);
        }
        if(!Files.exists(destDir)){
            touchCommand touch = new touchCommand(resolver) ;
            touch.execute(new String[] {destination});
        }
        else if(DirectoryChecker.isDirectory(destination,resolver)){
            destDir = destDir.resolve(sourceFile.getFileName());
        }

        try {
            Files.move(sourceFile, destDir, StandardCopyOption.REPLACE_EXISTING);
            return null ;
        }
        catch (Exception e) {
            return String.format("mv: cannot move '%s': %s", sourceFile, e.toString());
        }

    }
}
