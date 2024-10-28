package org.cli.commands;
import org.cli.commands.enums.CommandType;

import java.util.EnumSet;
import java.util.Map;
import java.util.LinkedHashMap;


public class helpCommand implements Command {
    private static Map<String, String> commands;
    private final EnumSet<helpCommand.Option> options;

    public enum Option {
        ;
        public static helpCommand.Option get(String option) throws IllegalArgumentException {
            switch (option) {
                default : throw new IllegalArgumentException(String.format("Invalid option %s", option));
            }
        }
    }

    static {
        commands = new LinkedHashMap<>();
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


    public helpCommand(){
        this.options = EnumSet.noneOf(helpCommand.Option.class);
    }

    public void enableOption(helpCommand.Option option) {
        options.add(option);
    }

    public void disableOption(helpCommand.Option option) {
        options.remove(option);
    }

    public boolean hasOption(helpCommand.Option option) {
        return options.contains(option);
    }


    @Override
    public void addOptions(String[] options) throws IllegalArgumentException {
        for(int i=0 ; i<options.length ; i++){
            this.enableOption( helpCommand.Option.get( options[i] ));
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
        StringBuilder helpMessage = new StringBuilder();
        for (Map.Entry<String, String> entry : commands.entrySet()) {
            helpMessage.append(String.format("  %s: %s%n", entry.getKey(), entry.getValue()));
        }
        return helpMessage.toString();
    }
}
