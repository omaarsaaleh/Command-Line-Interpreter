package org.cli.commands;

import org.cli.utils.DirectoryChecker;
import org.cli.utils.FileSystemManager;
import org.cli.utils.pathresolvers.PathResolver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

public class lsCommand implements Command{
    private final EnumSet<Option> options;

    private final PathResolver resolver ;

    public enum Option {
        ALL,
        RECURSIVE;

        public static Option get(String option) throws IllegalArgumentException {
            return switch (option) {
                case "-a", "--all" -> ALL;
                case "-R", "--recursive" -> RECURSIVE;
                default -> throw new IllegalArgumentException(String.format("Invalid option %s", option));
            };
        }
    }

    public lsCommand(PathResolver resolver)
    {
        this.resolver = resolver ;
        this.options = EnumSet.noneOf(Option.class);
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException{
        for(int i=0 ; i<options.length ; i++){
            this.enableOption( Option.get( options[i] ));
        }
    }

    public void enableOption(Option option) {
        options.add(option);
    }

    public void disableOption(Option option) {
        options.remove(option);
    }

    public boolean hasOption(Option option) {
        return options.contains(option);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.RETURNABLE ;
    }

    @Override
    public int getNumOfArguments() {
        return 1 ;
    }

    public StringBuilder List(Path path, Boolean isRecursive, Boolean showHidden, StringBuilder result, String currentDir ){

        List<Path> nxt = new ArrayList<>() ;
        try (Stream<Path> files = Files.list(path)) {
            result.append(String.format(".%s:\n", currentDir)) ;

            Iterable<Path> fileList = (Iterable<Path>) files::iterator ;
            boolean in = false ;
            for (Path file : fileList) {
                try {
                    if (!Files.isHidden(file) || showHidden) {
                        in = true ;
                        result.append(file.getFileName().toString()).append(" ");
                        if(Files.isDirectory(file)){
                            nxt.add(file) ;
                        }
                    }

                } catch (Exception e) {}
            }
            if(in) result.append("\n");
            result.append("\n");
            if(isRecursive) {
                for (Path dir : nxt) {
                    result = List(dir, isRecursive, showHidden, result,
                            currentDir + String.format("/%s", dir.getFileName().toString()));
                }
            }
        } catch (Exception e) {

        }

        return result ;
    }

    @Override
    public String execute(String[] args) {
        if(args.length == 0){
            args = new String[]{FileSystemManager.getInstance().getCurrentDirectory()} ;
        }

        StringBuilder result = new StringBuilder("") ;

        for(int i=0 ; i<args.length ; i++) {
            String pathStr = resolver.resolve(args[i]);
            Boolean isRecursive = this.hasOption(Option.RECURSIVE);
            Boolean showHidden = this.hasOption(Option.ALL);

            Path path = Paths.get(pathStr);
            if (!Files.exists(path)) {
                result.append (String.format("cannot access '%s': No such file or directory\n\n", pathStr));
                continue;
            }
            if (!DirectoryChecker.isDirectory(pathStr, resolver)) {
                result.append( path.getFileName().toString() + "\n\n" );
                continue;
            }
            String cur =
                    this.List(path, isRecursive, showHidden, new StringBuilder(), "").toString();
            if (cur != "") result.append(cur) ;
            else result.append( (String.format("ls: cannot access '%s'", pathStr)) );
        }

        String res = result.toString() ;
        return res.substring(0, res.length()-2);

    }
}
