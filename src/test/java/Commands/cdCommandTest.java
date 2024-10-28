package Commands;

import org.cli.utils.FileSystemManager;
import org.cli.utils.PathResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cli.commands.cdCommand;

public class cdCommandTest {
    private cdCommand cd ;
    private FileSystemManager fileManager ;

    @BeforeEach
    void setUp() {
        fileManager = FileSystemManager.getInstance();
        cd = new cdCommand();
    }

    @Test
    void testExecuteValidPath(){
        String validPath = "~/Documents";
        String result = cd.execute(new String[]{validPath});

        assertNull(result);
        assertEquals(PathResolver.resolve(validPath), fileManager.getCurrentDirectory());
    }

    @Test
    void testExecuteNonValidPath(){
        String curDir = fileManager.getCurrentDirectory() ;
        String validPath = "~/Documents/notavaliddirforsure55";

        assertEquals(curDir, fileManager.getCurrentDirectory());
        assertThrows(IllegalArgumentException.class, () -> cd.execute(new String[]{validPath}) );

    }

}
