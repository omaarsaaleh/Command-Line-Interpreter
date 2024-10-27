package org.cli.commands;
import org.cli.utils.pathresolvers.PathResolverFactory;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private static final Map<String, Supplier<Command>> commandMap = new HashMap<>();

    static {
        commandMap.put("cat", () -> new catCommand( PathResolverFactory.getResolver() ));
        commandMap.put("cd", () -> new cdCommand( PathResolverFactory.getResolver()  ));
        commandMap.put("ls", () -> new lsCommand( PathResolverFactory.getResolver() ));
        commandMap.put("mkdir", () -> new mkdirCommand(PathResolverFactory.getResolver() ));
        commandMap.put("mv", () -> new mvCommand(PathResolverFactory.getResolver() ));
        commandMap.put("pwd", pwdCommand::new);
        commandMap.put("rm", () -> new rmCommand(PathResolverFactory.getResolver() ));
        commandMap.put("rmdir", () -> new rmdirCommand(PathResolverFactory.getResolver() ));
        commandMap.put("touch", () -> new touchCommand(PathResolverFactory.getResolver() ));
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
