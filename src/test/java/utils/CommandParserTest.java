package utils;

import org.cli.commands.OutputDirection;
import org.cli.commands.ParsedCommand;
import org.cli.utils.pathresolvers.LinuxPathResolver;
import org.cli.utils.pathresolvers.PathResolver;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cli.utils.CommandParser;

import java.util.List;


public class CommandParserTest {


    @Test
    public void testSingleCommandParsing() {
        String command = "echo 'Hello, World!'";
        List<ParsedCommand> parsedCommands = CommandParser.parse(command, LinuxPathResolver.getInstance());

        assertEquals(1, parsedCommands.size());
        ParsedCommand parsedCmd = parsedCommands.getFirst();

        assertEquals("echo", parsedCmd.getCommandName());
        assertEquals(List.of("Hello, World!"), parsedCmd.getArgs());
        assertEquals(List.of(), parsedCmd.getOptions());
        assertEquals(OutputDirection.TERMINAL, parsedCmd.getOutputDirection());
        assertNull(parsedCmd.getFile());
    }


    @Test
    public void testCommandWithRedirections() {
        String command = "cat t.txt >> f.txt";
        List<ParsedCommand> parsedCommands = CommandParser.parse(command, LinuxPathResolver.getInstance());

        assertEquals(1, parsedCommands.size());
        ParsedCommand parsedCmd = parsedCommands.getFirst() ;

        assertEquals("cat", parsedCmd.getCommandName());
        assertEquals(List.of("t.txt"), parsedCmd.getArgs());
        assertEquals(List.of(), parsedCmd.getOptions());
        assertEquals(OutputDirection.FILE_APPEND, parsedCmd.getOutputDirection());
    }

    @Test
    public void testCommandWithPipe() {
        String command = "ls | grep 'test'";
        List<ParsedCommand> parsedCommands = CommandParser.parse(command, LinuxPathResolver.getInstance());

        assertEquals(2, parsedCommands.size());

        ParsedCommand firstCmd = parsedCommands.get(0);
        ParsedCommand secondCmd = parsedCommands.get(1);

        assertEquals("ls", firstCmd.getCommandName());
        assertEquals(OutputDirection.PIPE, firstCmd.getOutputDirection());

        assertEquals("grep", secondCmd.getCommandName());
        assertEquals(List.of("test"), secondCmd.getArgs() );
    }



}
