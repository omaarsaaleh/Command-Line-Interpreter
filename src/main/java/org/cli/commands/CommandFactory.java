package org.cli.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private static final Map<String, Supplier<Command>> commandMap = new HashMap<>();

    static {
        commandMap.put("cat", catCommand::new);
        commandMap.put("cd", cdCommand::new);
        commandMap.put("ls", lsCommand::new);
        commandMap.put("mkdir", mkdirCommand::new);
        commandMap.put("mv", mvCommand::new);
        commandMap.put("pwd", pwdCommand::new);
        commandMap.put("rm", rmCommand::new);
        commandMap.put("rmdir", rmdirCommand::new);
        commandMap.put("touch", touchCommand::new);
        commandMap.put("help", helpCommand::new);

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
