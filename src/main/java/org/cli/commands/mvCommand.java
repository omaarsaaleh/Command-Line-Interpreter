package org.cli.commands;

import org.cli.utils.DirectoryChecker;
import org.cli.utils.pathresolvers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.EnumSet;


public class mvCommand implements Command{
    // mv [source_file_name(s)] [Destination_file_name]

    private final PathResolver resolver ;

    private final EnumSet<mvCommand.Option> options;

    public enum Option {
        ;
        public static mvCommand.Option get(String option)  throws IllegalArgumentException {
            switch (option) {
                default : throw new IllegalArgumentException(String.format("Invalid option %s", option));
            }
        }
    }

    public mvCommand(PathResolver resolver){
        this.resolver = resolver ;
        this.options = EnumSet.noneOf(mvCommand.Option.class);
    }

    public void enableOption(mvCommand.Option option) {
        options.add(option);
    }

    public void disableOption(mvCommand.Option option) {
        options.remove(option);
    }

    public boolean hasOption(mvCommand.Option option) {
        return options.contains(option);
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException {
        for(int i=0 ; i<options.length ; i++){
            this.enableOption( mvCommand.Option.get( options[i] ));
        }
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.NON_RETURNABLE ;
    }

    @Override
    public int getNumOfArguments() {
        return 2 ;
    }


    @Override
    public String execute(String[] args) {
        if(args.length < this.getNumOfArguments()){
            throw new IllegalArgumentException("missing arguments");
        }
        if(args.length > this.getNumOfArguments()){
            throw new IllegalArgumentException("too many arguments");
        }

        String source = resolver.resolve(args[0]);
        String destination = resolver.resolve(args[1]);

        Path sourceFile = Paths.get(source);
        Path destDir = Paths.get(destination);

        if(!Files.exists(sourceFile)){
            throw new IllegalArgumentException( String.format("cannot stat '%s': No such file or directory", source) );
        }
        if(!DirectoryChecker.isParentDirectory(destination,resolver)){
            throw new IllegalArgumentException( String.format("cannot move '%s' to '%s': No such file or directory", source, destination) ) ;
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
            throw new IllegalArgumentException(String.format("cannot move '%s': %s", sourceFile, e.toString()));
        }

    }
}
