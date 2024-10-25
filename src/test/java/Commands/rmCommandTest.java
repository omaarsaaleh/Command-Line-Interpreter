package Commands;

import org.cli.commands.rmCommand;
import org.cli.utils.pathresolvers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


class rmCommandTest {

    private rmCommand command;
    private PathResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = LinuxPathResolver.getInstance();
        command = new rmCommand(resolver);
    }

    @Test
    void testRemoveNonExistentFile() {
        String filePath = "nonexistentfile.txt";
        String result = command.execute(new String[]{filePath});

        assertTrue(result.contains("No such file or directory") );
    }

    @Test
    void testRemoveDirectory() throws java.io.IOException{

        Path tempDir = Files.createTempDirectory("testdir");
        String result = command.execute(new String[]{tempDir.toString()});
        assertTrue(result.contains("Is a directory"));

    }

    @Test
    void testRemoveFileSuccessfully() throws java.io.IOException {
        Path tempFile = Files.createTempFile("testfile", ".txt");

        String result = command.execute(new String[]{tempFile.toString()});
        assertNull(result);
        assertFalse(Files.exists(tempFile));
    }


}
