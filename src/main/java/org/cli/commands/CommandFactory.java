package org.cli.commands;

import org.cli.utils.pathresolvers.LinuxPathResolver;
import org.cli.utils.pathresolvers.PathResolver;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private static final Map<String, Supplier<Command>> commandMap = new HashMap<>();

    static {
        commandMap.put("cat", () -> new catCommand(LinuxPathResolver.getInstance()));
        commandMap.put("cd", () -> new cdCommand(LinuxPathResolver.getInstance()));
        commandMap.put("ls", () -> new lsCommand(LinuxPathResolver.getInstance()));
        commandMap.put("mkdir", () -> new mkdirCommand(LinuxPathResolver.getInstance()));
        commandMap.put("mv", () -> new mvCommand(LinuxPathResolver.getInstance()));
        commandMap.put("pwd", pwdCommand::new);
        commandMap.put("rm", () -> new rmCommand(LinuxPathResolver.getInstance()));
        commandMap.put("rmdir", () -> new rmdirCommand(LinuxPathResolver.getInstance()));
        commandMap.put("touch", () -> new touchCommand(LinuxPathResolver.getInstance()));
    }

    public static Command getCommand(String commandName) {
        Supplier<Command> commandSupplier = commandMap.get(commandName);
        if (commandSupplier != null) {
            return commandSupplier.get();
        }
        else {
            throw new IllegalArgumentException("command not found");
        }
    }


}
