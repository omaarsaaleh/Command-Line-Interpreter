package org.cli.commands;

import org.cli.utils.DirectoryChecker;
import org.cli.utils.pathresolvers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

public class catCommand implements Command{
    private final PathResolver resolver ;
    private final EnumSet<catCommand.Option> options;

    public enum Option {
        ;
        public static catCommand.Option get(String option) throws IllegalArgumentException {
            switch (option) {
                default : throw new IllegalArgumentException(String.format("Invalid option %s", option));
            }
        }
    }

    public catCommand(PathResolver resolver){
        this.resolver = resolver ;
        this.options = EnumSet.noneOf(catCommand.Option.class);
    }

    public void enableOption(catCommand.Option option) {
        options.add(option);
    }

    public void disableOption(catCommand.Option option) {
        options.remove(option);
    }

    public boolean hasOption(catCommand.Option option) {
        return options.contains(option);
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException {
        for(int i=0 ; i<options.length ; i++){
            this.enableOption( catCommand.Option.get( options[i] ));
        }
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.RETURNABLE ;
    }

    @Override
    public int getNumOfArguments() {
        return 1 ;
    }

    @Override
    public String execute(String[] args) {
        if(args.length < this.getNumOfArguments()){
            throw new IllegalArgumentException("missing arguments");
        }
        if(args.length > this.getNumOfArguments()){
            throw new IllegalArgumentException("too many arguments");
        }

        String pathStr = args[0];
        pathStr = resolver.resolve(pathStr) ;

        Path file = Paths.get(pathStr);
        if (!Files.exists(file)) {
            throw new IllegalArgumentException( String.format("'%s': No such file or directory", pathStr) ) ;
        }
        if(DirectoryChecker.isDirectory(pathStr,resolver)){
            throw new IllegalArgumentException( String.format("'%s': Is a directory", pathStr)) ;
        }

        try {
            return String.join("\n", Files.readAllLines(Paths.get(pathStr)));
        }
        catch (SecurityException e){
            throw new IllegalArgumentException(String.format("'%s': Permission denied", pathStr));
        }
        catch (Exception e) {
            throw new IllegalArgumentException( String.format("'%s': %s", pathStr, e.getMessage())) ;
        }
    }
}
