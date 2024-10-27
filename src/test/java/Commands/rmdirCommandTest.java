package Commands;

import org.cli.commands.rmdirCommand;
import org.cli.utils.pathresolvers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class rmdirCommandTest {
    private PathResolver resolver;
    private rmdirCommand command;
    private Path tempDir;

    @BeforeEach
    public void setUp() throws Exception {
        resolver = PathResolverFactory.getResolver();
        command = new rmdirCommand(resolver);
        tempDir = Files.createTempDirectory("testRmdir");
    }

    @Test
    public void testRemoveNonDirectory() throws Exception {
        Path nonDirFile = Files.createFile(tempDir.resolve("file.txt"));
        String pathStr = nonDirFile.toString();

        assertThrows(IllegalArgumentException.class, () -> command.execute(new String[]{pathStr}));

    }

    @Test
    public void testRemoveNonEmptyDirectory() throws Exception {
        Path nonEmptyDir = Files.createDirectory(tempDir.resolve("nonEmptyDir"));
        Files.createFile(nonEmptyDir.resolve("file.txt"));

        String pathStr = nonEmptyDir.toString();

        assertThrows(IllegalArgumentException.class, () -> command.execute(new String[]{pathStr}));

    }

    @Test
    public void testRemoveEmptyDirectory() throws Exception {
        Path emptyDir = Files.createDirectory(tempDir.resolve("emptyDir"));

        String pathStr = emptyDir.toString();
        String result = command.execute(new String[]{pathStr});

        assertNull(result);
        assertFalse(Files.exists(emptyDir));
    }

}