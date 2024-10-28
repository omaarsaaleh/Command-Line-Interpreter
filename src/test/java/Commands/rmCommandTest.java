package Commands;

import org.cli.commands.rmCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


class rmCommandTest {

    private rmCommand command;

    @BeforeEach
    void setUp() {
        command = new rmCommand();
    }

    @Test
    void testRemoveNonExistentFile() {
        String filePath = "nonexistentfile.txt";
        assertThrows(IllegalArgumentException.class, () ->  command.execute(new String[]{filePath}) );

    }

    @Test
    void testRemoveDirectory() throws java.io.IOException{

        Path tempDir = Files.createTempDirectory("testdir");
        assertThrows(IllegalArgumentException.class, () -> command.execute(new String[]{tempDir.toString()}));


    }

    @Test
    void testRemoveFileSuccessfully() throws java.io.IOException {
        Path tempFile = Files.createTempFile("testfile", ".txt");

        String result = command.execute(new String[]{tempFile.toString()});
        assertNull(result);
        assertFalse(Files.exists(tempFile));
    }


}
