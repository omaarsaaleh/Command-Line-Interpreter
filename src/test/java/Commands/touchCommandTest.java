package Commands;

import org.cli.commands.touchCommand;
import org.cli.utils.pathresolvers.*;
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
    private PathResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = PathResolverFactory.getResolver();
        touch = new touchCommand(resolver);
    }

    @Test
    void testExecuteValid(){
        String validPath = "~/Documents/tst11";
        String result = touch.execute(new String[]{validPath});

        assertNull(result, "Error Creating the file (not the expected return)");

        Path createdFile = Paths.get(resolver.resolve(validPath));
        assertTrue(Files.exists(createdFile),"Error Creating the file (exist test failed)");
    }

    @Test
    void testExecuteAlreadyExists() throws IOException, InterruptedException{
        String validPath = "~/Documents/tst12";

        touch.execute(new String[]{validPath});
        FileTime fileTime = FileTime.fromMillis(System.currentTimeMillis());
        Thread.sleep(10);

        String result = touch.execute(new String[]{validPath});

        Path file = Paths.get( resolver.resolve(validPath)) ;
        FileTime updatedtime = Files.getLastModifiedTime(file) ;
        FileTime accesstime = (FileTime) Files.getAttribute(file, "lastAccessTime");

        assertNotEquals(fileTime, updatedtime );
        assertNotEquals(fileTime, accesstime);

    }

    @Test
    void testExecuteInvalidPath(){
        String invalidPath = "~/rrrererer3d/tst11";
        assertThrows(IllegalArgumentException.class, () -> touch.execute(new String[]{invalidPath}));
    }

}
