package org.cli.commands;
import java.util.Map;
import java.util.HashMap;


public class helpCommand implements Command {
    private static Map<String, String> commands;

    static {
        commands = new HashMap<>();
        commands.put("pwd", "Print the current working directory.");
        commands.put("cd", "Change the current directory.");
        commands.put("ls", "List directory contents.");
        commands.put("mkdir", "Create a new directory.");
        commands.put("rmdir", "Remove an empty directory.");
        commands.put("touch", "Create an empty file or update the timestamp of a file.");
        commands.put("mv", "Move or rename files and directories.");
        commands.put("rm", "Remove files or directories.");
        commands.put("cat", "Concatenate and display the content of files.");
        commands.put("help", "Display Commands and their usage");
        commands.put("exit", "Exit the The Terminal.");
    }

    @Override
    public void addOptions(String[] options) throws IllegalArgumentException {
        //
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
        for (Map.Entry<String, String> entry : commands.entrySet()) {
            System.out.printf("  %s: %s%n", entry.getKey(), entry.getValue());
        }
        return null;
    }
}
