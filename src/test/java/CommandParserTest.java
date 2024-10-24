import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cli.utils.CommandParser;

import java.util.List;


public class CommandParserTest {
    @Test
    public void testLeadingAndTrailingSpaces() {
        String cmd = "   cd   folder   ";
        List<String> result = CommandParser.parseCommand(cmd);
        assertEquals(List.of("cd", "folder"), result);
    }

    @Test
    void testDoubleQuotedArguments(){
        String cmd = "cd \"my folder\"";
        List<String> result = CommandParser.parseCommand(cmd);
        assertEquals(List.of("cd", "my folder"), result);
    }

    @Test
    void testSingleQuotedArguments(){
        String cmd = "cd 'my folder 2'";
        List<String> result = CommandParser.parseCommand(cmd);
        assertEquals(List.of("cd", "my folder 2"), result);
    }

    @Test
    public void testMixedQuotedAndUnquoted() {
        String cmd = "cd \"my folder\" bar 'foo'";
        List<String> result = CommandParser.parseCommand(cmd);
        assertEquals(List.of("cd", "my folder", "bar", "foo"), result);
    }





}
