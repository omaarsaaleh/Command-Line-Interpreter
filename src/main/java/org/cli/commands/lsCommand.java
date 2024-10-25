package org.cli.commands;

import org.cli.utils.DirectoryChecker;
import org.cli.utils.pathresolvers.PathResolver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class lsCommand implements Command{
    private PathResolver resolver ;
    public lsCommand(PathResolver resolver){
        this.resolver = resolver ;
    }
    @Override
    public String execute(String[] args) {

        String pathStr = resolver.resolve( args[0] ) ;

        Path path = Paths.get(pathStr);
        if(!Files.exists(path)){
            return String.format("ls: cannot access '%s': No such file or directory", pathStr);
        }
        if(!DirectoryChecker.isDirectory(pathStr,resolver)){
            return path.getFileName().toString();
        }
        try (Stream<Path> files = Files.list(path)) {

            String result = files.filter(file -> {
                try { return !Files.isHidden(file) ;}
                catch (Exception e){
                    return false ;
                }
            }).map(Path::getFileName).map(Path::toString).collect(Collectors.joining(" "));

            return result ;
        }
        catch (Exception e) {
            return String.format("ls: cannot access '%s': %s", pathStr, e.getMessage());
        }
    }
}
