package utils;

import org.cli.utils.FileSystemManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.cli.utils.DirectoryChecker;

public class DirectoryCheckerTest {
    private FileSystemManager fileManager;

    @BeforeEach
    void setUp() {
        fileManager = FileSystemManager.getInstance();
    }

    @Test
    void testNull() {
        String ValidDir = null;
        assertFalse(DirectoryChecker.isDirectory(ValidDir));
    }

    @Test
    void testValidDir() {
        String ValidDir = "~";
        assertTrue(DirectoryChecker.isDirectory(ValidDir));
    }

    @Test
    void testNonExistDir() {
        String NonExistDir = "~/notavaliddir55535";
        assertFalse(DirectoryChecker.isDirectory(NonExistDir));
    }

    @Test
    void testValidParentDir(){
        String ValidDir = "~";
        assertTrue(DirectoryChecker.isParentDirectory(ValidDir));
    }

    @Test
    void testNonValidParentDir(){
        String NonValidDir = "/";
        assertFalse(DirectoryChecker.isParentDirectory(NonValidDir));
    }

}
