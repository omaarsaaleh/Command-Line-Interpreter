package org.cli.commands;
import org.cli.utils.FileSystemManager;
import java.io.File;
public class mkdirCommand implements Command{
    public String execute(String[] args) {

        String path = FileSystemManager.getInstance().getCurrentDirectory();
        String dirName = args[0];
        File directory = new File(path, dirName);
        if (directory.mkdir()) {
            return null;
        } else {
            return "Directory already exists or failed to create.";
        }

    }
}
