package Commands;

import org.cli.commands.touchCommand;
import org.cli.utils.PathResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import static org.junit.jupiter.api.Assertions.*;

public class touchCommandTest {
    private touchCommand touch ;

    @BeforeEach
    void setUp() {
        touch = new touchCommand();
    }

    @Test
    void testExecuteValid(){
        String validPath = "~/Documents/tst11";
        String result = touch.execute(new String[]{validPath});

        assertNull(result, "Error Creating the file (not the expected return)");

        Path createdFile = Paths.get(PathResolver.resolve(validPath));
        assertTrue(Files.exists(createdFile),"Error Creating the file (exist test failed)");

        if (Files.exists(createdFile)) {
            try {
                Files.delete(createdFile);
            }
            catch (IOException e) {}
        }
    }

    @Test
    void testExecuteAlreadyExists() throws IOException, InterruptedException{
        String validPath = "~/Documents/tst12";

        touch.execute(new String[]{validPath});
        FileTime fileTime = FileTime.fromMillis(System.currentTimeMillis());
        Thread.sleep(10);

        String result = touch.execute(new String[]{validPath});

        Path file = Paths.get( PathResolver.resolve(validPath)) ;
        FileTime updatedtime = Files.getLastModifiedTime(file) ;
        FileTime accesstime = (FileTime) Files.getAttribute(file, "lastAccessTime");

        assertNotEquals(fileTime, updatedtime );
        assertNotEquals(fileTime, accesstime);

        if (Files.exists(file)) {
            Files.delete(file);
        }

    }

    @Test
    void testExecuteInvalidPath(){
        String invalidPath = "~/rrrererer3d/tst11";
        assertThrows(IllegalArgumentException.class, () -> touch.execute(new String[]{invalidPath}));
    }



}
