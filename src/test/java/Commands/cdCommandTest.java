package Commands;

import org.cli.utils.FileSystemManager;
import org.cli.utils.pathresolvers.LinuxPathResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cli.commands.cdCommand;
import org.cli.utils.pathresolvers.*;

import javax.naming.spi.Resolver;

public class cdCommandTest {
    private cdCommand cd ;
    private PathResolver resolver;
    private FileSystemManager fileManager ;

    @BeforeEach
    void setUp() {
        resolver = LinuxPathResolver.getInstance();
        fileManager = FileSystemManager.getInstance();
        cd = new cdCommand(resolver);
    }

    @Test
    void testExecuteValidPath(){
        String validPath = "~/Documents";
        String result = cd.execute(new String[]{validPath});

        assertNull(result);
        assertEquals(resolver.resolve(validPath), fileManager.getCurrentDirectory());
    }

    @Test
    void testExecuteNonValidPath(){
        String curDir = fileManager.getCurrentDirectory() ;
        String validPath = "~/Documents/notavaliddirforsure55";
        String result = cd.execute(new String[]{validPath});


        assertEquals("Cannot find path", result);
        assertEquals(curDir, fileManager.getCurrentDirectory());
    }

}
