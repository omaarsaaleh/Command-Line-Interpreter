package org.cli.commands;
import org.cli.commands.enums.CommandType;
import org.cli.utils.FileSystemManager;
import org.cli.utils.PathResolver;

import java.util.EnumSet;


public class cdCommand implements Command{

    private final EnumSet<cdCommand.Option> options;

    public enum Option {
        ;
        public static cdCommand.Option get(String option) throws IllegalArgumentException {
            switch (option) {
                default : throw new IllegalArgumentException(String.format("Invalid option %s", option));
            }
        }
    }

    public cdCommand(){
        this.options = EnumSet.noneOf(cdCommand.Option.class);
    }

    public void enableOption(cdCommand.Option option) {
        options.add(option);
    }

    public void disableOption(cdCommand.Option option) {
        options.remove(option);
    }

    public boolean hasOption(cdCommand.Option option) {
        return options.contains(option);
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException{
        for(int i=0 ; i<options.length ; i++){
            this.enableOption( cdCommand.Option.get( options[i] ));
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

    @Override
    public String execute(String[] args) {
        if(args.length < this.getNumOfArguments()){
            throw new IllegalArgumentException("missing arguments");
        }
        if(args.length > this.getNumOfArguments()){
            throw new IllegalArgumentException("too many arguments");
        }

        String path = PathResolver.resolve(args[0]);
        try {
            FileSystemManager.getInstance().changeDirectory(path);
            return null;
        }
        catch (Exception e) {
            throw new IllegalArgumentException( String.format("%s: No such file or directory", path));
        }
    }
}

