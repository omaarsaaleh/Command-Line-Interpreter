package org.cli.commands;
import org.cli.utils.FileSystemManager;
import org.cli.utils.pathresolvers.LinuxPathResolver;
import org.cli.utils.pathresolvers.PathResolver;

import java.util.EnumSet;

public class pwdCommand implements Command{

    private final EnumSet<pwdCommand.Option> options;

    public enum Option {
        ;
        public static pwdCommand.Option get(String option) throws IllegalArgumentException {
            switch (option) {
                default : throw new IllegalArgumentException(String.format("Invalid option %s", option));
            }
        }
    }

    public pwdCommand(){
        this.options = EnumSet.noneOf(pwdCommand.Option.class);

    }

    public void enableOption(pwdCommand.Option option) {
        options.add(option);
    }

    public void disableOption(pwdCommand.Option option) {
        options.remove(option);
    }

    public boolean hasOption(pwdCommand.Option option) {
        return options.contains(option);
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException {
        for(int i=0 ; i<options.length ; i++){
            this.enableOption( pwdCommand.Option.get( options[i] ));
        }
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.RETURNABLE ;
    }

    @Override
    public int getNumOfArguments() {
        return 0 ;
    }

    @Override
    public String execute(String[] args){
        return FileSystemManager.getInstance().getCurrentDirectory();
    }
}
