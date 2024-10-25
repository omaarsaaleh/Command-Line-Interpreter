package utils.pathresolvers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cli.utils.pathresolvers.LinuxPathResolver;
import org.cli.utils.FileSystemManager;

import java.nio.file.InvalidPathException;

public class LinuxPathResolverTest {
    private LinuxPathResolver resolver;
    private FileSystemManager fileManager;

    @BeforeEach
    void setUp() {
        resolver = (LinuxPathResolver) LinuxPathResolver.getInstance();
        fileManager = FileSystemManager.getInstance();
    }

    @Test
    void testGetInstance() {
        LinuxPathResolver firstInstance = (LinuxPathResolver) LinuxPathResolver.getInstance();
        assertSame(firstInstance, resolver, "getInstance should return the same instance");
    }

    @Test
    void testResolveCurrentDirRelativePath() {
        String inputPath = "myfile.txt";
        String resolvedPath = resolver.resolve(inputPath);
        String expected = fileManager.getCurrentDirectory() + "/" + inputPath ;
        assertEquals(expected, resolvedPath, "Resolved path for current directory relative path should be correct");
    }

    @Test
    void testResolveAbsolutePath() {
        String inputPath = "/usr/local/bin";
        String resolvedPath = resolver.resolve(inputPath);
        assertEquals("/usr/local/bin", resolvedPath, "Resolved path should be the same for absolute paths");
    }

    @Test
    void testResolveHomePath() {
        String inputPath = "~/documents";
        String resolvedPath = resolver.resolve(inputPath);
        String expectedPath = fileManager.getHomeDirectory() + "documents";
        assertEquals(expectedPath, resolvedPath, "Resolved path for home relative path should be correct");
    }

    @Test
    void testResolveNormalization() {
        String inputPath = "/usr/../local/bin/./myfile.txt";
        String resolvedPath = resolver.resolve(inputPath);
        assertEquals("/local/bin/myfile.txt", resolvedPath, "Resolved path should normalize the input path correctly");
    }

}
