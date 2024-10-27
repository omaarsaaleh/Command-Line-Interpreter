package org.cli.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomFileWriter {

    public static String write(String path, String content, boolean append){
        try {
            if (!Files.exists(Paths.get(path))) {
                return String.format( "'%s': No such file or directory", path );
            }
        }
        catch (SecurityException e) {
            return String.format("'%s': cannot access file or directory", path);
        }
        try (FileWriter writer = new FileWriter(path, append)) {
            writer.write(content);
            writer.flush();
            return null ;
        }
        catch (IOException e) {
            return String.format("'%s': Error overwriting file", path);
        }
    }
}
