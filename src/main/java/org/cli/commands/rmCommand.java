package org.cli.commands;
import org.cli.utils.FileSystemManager;
import java.io.File;

public class rmCommand implements Command{

    public String execute(String[] args) {
        if (args.length == 0) {
            return "No file or directory specified.";
        }

        String path = FileSystemManager.getInstance().getCurrentDirectory();
        String name = args[0];
        File file = new File(path, name);

        if (!file.exists()) {
            return "File or directory does not exist.";
        }

        if (file.isDirectory()) {
            return "rm: cannot remove '" + name + "/': Is a directory";
        } else { // It's a file
            if (file.delete()) {
                return null;
            } else {
                return "Failed to remove the file.";
            }
        }
    }

}
