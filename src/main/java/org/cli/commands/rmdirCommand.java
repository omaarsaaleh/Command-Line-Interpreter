package org.cli.commands;
import org.cli.commands.enums.CommandType;
import org.cli.utils.DirectoryChecker;
import org.cli.utils.PathResolver;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

public class rmdirCommand implements Command{

    private final EnumSet<rmdirCommand.Option> options;

    public enum Option {
        ;
        public static rmdirCommand.Option get(String option) throws IllegalArgumentException {
            switch (option) {
                default : throw new IllegalArgumentException(String.format("Invalid option %s", option));
            }
        }
    }

    public rmdirCommand(){
        this.options = EnumSet.noneOf(rmdirCommand.Option.class);
    }

    public void enableOption(rmdirCommand.Option option) {
        options.add(option);
    }

    public void disableOption(rmdirCommand.Option option) {
        options.remove(option);
    }

    public boolean hasOption(rmdirCommand.Option option) {
        return options.contains(option);
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException {
        for(int i=0 ; i<options.length ; i++){
            this.enableOption( rmdirCommand.Option.get( options[i] ));
        }
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.NON_RETURNABLE;
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
        Path directory = Paths.get(pathStr);

        if(!Files.exists(directory)){
            throw new IllegalArgumentException(String.format("failed to remove '%s': No such file or directory", pathStr)) ;
        }

        if(!DirectoryChecker.isDirectory(pathStr))
        {
            throw new IllegalArgumentException(  String.format("failed to remove '%s': Not a directory", pathStr) );
        }


        try  {
            if(Files.list(directory).findAny().isEmpty()){
                Files.delete(directory);
                return null ;
            }
        }
        catch (SecurityException e){
            throw new IllegalArgumentException( String.format("failed to remove '%s': Operation not permitted", pathStr) );
        }
        catch (Exception e) {
            throw new IllegalArgumentException( String.format("failed to remove '%s': %s", pathStr, e.getMessage()) );
        }

        throw new IllegalArgumentException( String.format("failed to remove '%s': Directory not empty", pathStr) );


    }
}
