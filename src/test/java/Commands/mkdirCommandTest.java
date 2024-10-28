package Commands;

import org.cli.commands.mkdirCommand;
import org.cli.utils.PathResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class mkdirCommandTest {
    private mkdirCommand cd ;

    @BeforeEach
    void setUp() {
        cd = new mkdirCommand();
    }

    @Test
    void testExecuteValid(){
        String validPath = "~/Documents/tst1111";
        String result = cd.execute(new String[]{validPath});

        assertNull(result);

        Path createdDir = Paths.get(PathResolver.resolve(validPath));
        assertTrue(Files.exists(createdDir));
    }

    @Test
    void testExecuteAlreadyExists(){
        String validPath = "~/Documents";

        Path createdDir = Paths.get(PathResolver.resolve(validPath));
        assertTrue(Files.exists(createdDir));

        assertThrows(IllegalArgumentException.class, () -> cd.execute(new String[]{validPath}));

    }

    @Test void testExecuteInvalidPath(){
        String invalidPath = "~/rrrererer3d/tst11";
        assertThrows(IllegalArgumentException.class, () -> cd.execute(new String[]{invalidPath}));

    }
}
