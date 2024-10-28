package org.cli.commands;

import org.cli.commands.enums.CommandType;
import org.cli.commands.enums.OutputDirection;

import java.util.List;

public class ParsedCommand {
    private final String COMMAND ;
    private final List<String> ARGS ;
    private final List<String> OPTIONS ;
    private final OutputDirection OUTPUT ;
    private final String FILE ;
    private CommandType cmdType ;
    private ExecutionStatus executionStatus ;

    public enum ExecutionStatus{
        NULL,
        SUCCESS,
        FAIL
    }

    public ParsedCommand(String cmd, List<String> args, List<String> options, OutputDirection output, String file){
        this.COMMAND = cmd ;
        this.ARGS = args ;
        this.OPTIONS = options ;
        this.OUTPUT = output ;
        this.FILE = file ;
        this.executionStatus = ExecutionStatus.NULL;
    }

    public OutputDirection getOutputDirection() {
        return this.OUTPUT;
    }

    public String getFile() {
        return this.FILE;
    }

    public String getCommandName() {
        return this.COMMAND;
    }

    public List<String> getArgs() {
        return this.ARGS;
    }

    public List<String> getOptions() {
        return this.OPTIONS;
    }

    public ExecutionStatus getexecutionStatus() {
        return this.executionStatus;
    }

    public CommandType getcmdType() {
        return this.cmdType;
    }

    public void addPipedOutput(String pipedOutput){
        if (this.ARGS.isEmpty()){
            this.ARGS.add(pipedOutput) ;
        }
    }

    public String Execute(){
        try{
            Command cmd = CommandFactory.getCommand(COMMAND) ;
            cmd.addOptions(OPTIONS.toArray(new String[0])) ;
            this.cmdType = cmd.getCommandType() ;
            String res = cmd.execute(ARGS.toArray(new String[0])) ;
            this.executionStatus = ExecutionStatus.SUCCESS ;
            return res;
        }
        catch (Exception e) {
            this.executionStatus = ExecutionStatus.FAIL ;
            return String.format( "%s: %s", COMMAND, e.getMessage()) ;

        }
    }

    @Override
    public String toString() {
        return String.format("ParsedCommand{COMMAND='%s', ARGS=%s, OPTIONS=%s, OUTPUT=%s, FILE=%s}",
                COMMAND,
                ARGS != null ? ARGS.toString() : "[]",
                OPTIONS != null ? OPTIONS.toString() : "[]",
                OUTPUT,
                FILE);
    }


}
