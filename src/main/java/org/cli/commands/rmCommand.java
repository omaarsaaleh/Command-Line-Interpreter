package org.cli.commands;
import org.cli.commands.enums.CommandType;
import org.cli.utils.DirectoryChecker;
import org.cli.utils.PathResolver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

public class rmCommand implements Command{

    private final EnumSet<rmCommand.Option> options;

    public enum Option {
        ;
        public static rmCommand.Option get(String option) throws IllegalArgumentException {
            switch (option) {
                default : throw new IllegalArgumentException(String.format("Invalid option %s", option));
            }
        }
    }

    public rmCommand(){
        this.options = EnumSet.noneOf(rmCommand.Option.class);
    }

    public void enableOption(rmCommand.Option option) {
        options.add(option);
    }

    public void disableOption(rmCommand.Option option) {
        options.remove(option);
    }

    public boolean hasOption(rmCommand.Option option) {
        return options.contains(option);
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException{
        for(int i=0 ; i<options.length ; i++){
            this.enableOption( rmCommand.Option.get( options[i] ));
        }
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.NON_RETURNABLE ;
    }

    @Override
    public int getNumOfArguments() {
        return 1 ;
    }


    public String execute(String[] args) {
        if(args.length < this.getNumOfArguments()){
            throw new IllegalArgumentException("missing arguments");
        }
        if(args.length > this.getNumOfArguments()){
            throw new IllegalArgumentException("too many arguments");
        }

        String pathStr = args[0];
        pathStr = PathResolver.resolve(pathStr) ;
        if(DirectoryChecker.isDirectory(pathStr))
        {
            throw new IllegalArgumentException(  String.format("cannot remove '%s': Is a directory", pathStr) );
        }
        Path file = Paths.get(pathStr);

        if (!Files.exists(file)) {
            throw new IllegalArgumentException(  String.format("cannot remove '%s': No such file or directory", pathStr));
        }

        try  {
            Files.delete(file);
            return null;
        }
        catch (SecurityException e){
            throw new IllegalArgumentException(  String.format("failed to remove '%s': Operation not permitted", pathStr) );
        }
        catch (Exception e) {
            throw new IllegalArgumentException(  String.format("failed to remove '%s': %s", pathStr, e.getMessage()) );
        }


    }

}
