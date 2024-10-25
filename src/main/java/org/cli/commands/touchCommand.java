package org.cli.commands;
import org.cli.utils.FileSystemManager;
import java.io.File;
import java.io.IOException;

public class touchCommand implements Command {
    public String execute(String[] args) {
        String path = FileSystemManager.getInstance().getCurrentDirectory();
        String fileName = args[0];
        File file = new File(path, fileName);
        try {
            if (file.createNewFile()) {
                return null;
            } else {
                // If the file already exists, just update its timestamp
                if (file.setLastModified(System.currentTimeMillis())) {
                    return null;
                } else {
                    return "Failed to update the timestamp of the file.";
                }
            }
        } catch (IOException e) {
            return "An error occurred while creating the file: " + e.getMessage();
        }
    }
}