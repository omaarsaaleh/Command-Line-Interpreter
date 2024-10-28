package org.cli.commands;
import org.cli.commands.enums.CommandType;
import org.cli.utils.PathResolver;
import org.cli.utils.DirectoryChecker ;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

public class mkdirCommand implements Command{

    private final EnumSet<mkdirCommand.Option> options;

    public enum Option {
        ;
        public static mkdirCommand.Option get(String option) throws IllegalArgumentException {
            switch (option) {
                default : throw new IllegalArgumentException(String.format("Invalid option %s", option));
            }
        }
    }

    public mkdirCommand(){
        this.options = EnumSet.noneOf(mkdirCommand.Option.class);
    }

    public void enableOption(mkdirCommand.Option option) {
        options.add(option);
    }

    public void disableOption(mkdirCommand.Option option) {
        options.remove(option);
    }

    public boolean hasOption(mkdirCommand.Option option) {
        return options.contains(option);
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException{
        for(int i=0 ; i<options.length ; i++){
            this.enableOption( mkdirCommand.Option.get( options[i] ));
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
        if(!DirectoryChecker.isParentDirectory(pathStr))
        {
            throw new IllegalArgumentException( String.format("cannot create directory ‘%s’: No such file or directory", pathStr));
        }
        pathStr = PathResolver.resolve(pathStr);

        Path path = Paths.get(pathStr);

        if (Files.exists(path)) {
            throw new IllegalArgumentException( String.format("cannot create directory ‘%s’: File exists", pathStr) );
        }
        else {
            try {
                Files.createDirectory(path);
                return null;
            }
            catch (IOException e) {
                throw new IllegalArgumentException(  "An error occurred while creating the directory: " + e.getMessage() );
            }
        }
    }
}
