package org.cli.commands;
import org.cli.utils.FileSystemManager;

import java.io.File;
public class rmdirCommand implements Command{
    public String execute(String[] args) {
        String path = FileSystemManager.getInstance().getCurrentDirectory();
        String dirName = args[0];
        File directory = new File(path, dirName);
        if (!directory.exists() || !directory.isDirectory()) {
            return "Directory does not exist or is not a directory.";
        }
        if (directory.list().length == 0) {
            if (directory.delete()) {
                return null;
            } else {
                return "Failed to remove the directory.";
            }
        } else {
            return "Directory is not empty.";
        }
    }
}
