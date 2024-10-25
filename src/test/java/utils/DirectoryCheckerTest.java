package utils;

import org.cli.utils.FileSystemManager;
import org.cli.utils.pathresolvers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.cli.utils.DirectoryChecker;

public class DirectoryCheckerTest {
    private PathResolver resolver;
    private FileSystemManager fileManager;

    @BeforeEach
    void setUp() {
        resolver = LinuxPathResolver.getInstance();
        fileManager = FileSystemManager.getInstance();
    }

    @Test
    void testNull() {
        String ValidDir = null;
        assertFalse(DirectoryChecker.isDirectory(ValidDir, resolver));
    }

    @Test
    void testValidDir() {
        String ValidDir = "~";
        assertTrue(DirectoryChecker.isDirectory(ValidDir, resolver));
    }

    @Test
    void testNonValidDir() {
        String NonValidDir = "~/notavaliddir55535";
        assertFalse(DirectoryChecker.isDirectory(NonValidDir, resolver));
    }

    @Test
    void testValidParentDir(){
        String ValidDir = "/usr/include";
        assertTrue(DirectoryChecker.isParentDirectory(ValidDir, resolver));
    }

    @Test
    void testNonValidParentDir(){
        String NonValidDir = "/";
        assertFalse(DirectoryChecker.isParentDirectory(NonValidDir, resolver));
    }

}
