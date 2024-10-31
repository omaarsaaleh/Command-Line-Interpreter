package org.cli.commands;

import org.cli.commands.enums.CommandType;
import org.cli.utils.DirectoryChecker;
import org.cli.utils.FileSystemManager;
import org.cli.utils.PathResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class lsCommand implements Command {
    private final EnumSet<Option> options;

    public enum Option {
        ALL,
        REVERSE;

        public static Option get(String option) throws IllegalArgumentException {
            return switch (option) {
                case "-a", "--all" -> ALL;
                case "-r", "--reverse" -> REVERSE;
                default -> throw new IllegalArgumentException(String.format("Invalid option %s", option));
            };
        }
    }

    public lsCommand() {
        this.options = EnumSet.noneOf(Option.class);
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException {
        for (String option : options) {
            this.enableOption(Option.get(option));
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
        return CommandType.RETURNABLE;
    }

    @Override
    public int getNumOfArguments() {
        return 1;
    }

    @Override
    public String execute(String[] args) {
        if (args.length == 0) {
            args = new String[]{FileSystemManager.getInstance().getCurrentDirectory()};
        }
        StringBuilder result = new StringBuilder();
        boolean showHidden = this.hasOption(Option.ALL);
        boolean reverseOrder = this.hasOption(Option.REVERSE);

        for (String arg : args) {
            String pathStr = PathResolver.resolve(arg);
            Path path = Paths.get(pathStr);

            if (!Files.exists(path)) {
                result.append(String.format("cannot access '%s': No such file or directory\n\n", pathStr));
                continue;
            }

            if (!DirectoryChecker.isDirectory(pathStr)) {
                result.append(path.getFileName().toString()).append("\n\n");
                continue;
            }

            List<Path> filesList = new ArrayList<>();
            try (Stream<Path> files = Files.list(path)) {

                filesList = files
                        .filter(file -> { boolean hid = false ; try{ hid=!Files.isHidden(file);} catch (IOException _) {}  return showHidden || hid;  } )
                        .sorted((p1, p2) -> reverseOrder ? p2.compareTo(p1) : p1.compareTo(p2))
                        .toList();

                result.append(".:\n");
                for (Path file : filesList) {
                    result.append(file.getFileName().toString());
                    if (Files.isDirectory(file)) {
                        result.append("/");
                    }
                    result.append(" ");
                }
            }
            catch (Exception e) {
                result.append(String.format("cannot access '%s'", pathStr));
            }
        }
        return result.toString();
    }
}
