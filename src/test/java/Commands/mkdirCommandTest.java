package Commands;

import com.sun.source.tree.AssertTree;
import org.cli.commands.mkdirCommand;
import org.cli.utils.FileSystemManager;
import org.cli.utils.pathresolvers.LinuxPathResolver;
import org.cli.utils.pathresolvers.PathResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class mkdirCommandTest {
    private mkdirCommand cd ;
    private PathResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = LinuxPathResolver.getInstance();
        cd = new mkdirCommand(resolver);
    }

    @Test
    void testExecuteValid(){
        String validPath = "~/Documents/tst11";
        String result = cd.execute(new String[]{validPath});

        assertNull(result);

        Path createdDir = Paths.get(resolver.resolve(validPath));
        assertTrue(Files.exists(createdDir));
    }

    @Test
    void testExecuteAlreadyExists(){
        String validPath = "~/Documents";

        String result = cd.execute(new String[]{validPath});

        assertTrue(result.contains("File exists"));

        Path createdDir = Paths.get(resolver.resolve(validPath));
        assertTrue(Files.exists(createdDir));
    }

    @Test void testExecuteInvalidPath(){
        String invalidPath = "~/rrrererer3d/tst11";
        String result = cd.execute(new String[]{invalidPath});
        assertTrue(result.contains("No such file or directory"));
    }
}
