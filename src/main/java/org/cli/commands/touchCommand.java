package org.cli.commands;
import org.cli.utils.DirectoryChecker;
import org.cli.utils.pathresolvers.PathResolver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.EnumSet;

public class touchCommand implements Command {

    private final PathResolver resolver ;

    private final EnumSet<touchCommand.Option> options;

    public enum Option {
        ;
        public static touchCommand.Option get(String option) throws IllegalArgumentException {
            switch (option) {
                default : throw new IllegalArgumentException(String.format("Invalid option %s", option));
            }
        }
    }

    public touchCommand(PathResolver resolver){
        this.resolver = resolver ;
        this.options = EnumSet.noneOf(touchCommand.Option.class);
    }

    public void enableOption(touchCommand.Option option) {
        options.add(option);
    }

    public void disableOption(touchCommand.Option option) {
        options.remove(option);
    }

    public boolean hasOption(touchCommand.Option option) {
        return options.contains(option);
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException {
        for(int i=0 ; i<options.length ; i++){
            this.enableOption( touchCommand.Option.get( options[i] ));
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
        String pathStr = args[0];
        if(!DirectoryChecker.isParentDirectory(pathStr,resolver))
        {
            throw new IllegalArgumentException(  String.format("cannot touch '%s: No such file or directory", pathStr) );
        }
        pathStr = resolver.resolve(pathStr);
        Path path = Paths.get(pathStr);

        if (Files.exists(path)) {
            try{
                FileTime fileTime = FileTime.fromMillis(System.currentTimeMillis());
                Files.setLastModifiedTime(path, fileTime);
                Files.setAttribute(path, "lastAccessTime", fileTime);
                return null ;
            }
            catch (IOException e) {
                throw new IllegalArgumentException(   "An error occurred while creating the directory: " + e.getMessage() );
            }
        }
        else {
            try {
                Files.createFile(path);
                return null;
            }
            catch (IOException e) {
                throw new IllegalArgumentException(   "An error occurred while creating the directory: " + e.getMessage() );
            }
        }

    }
}