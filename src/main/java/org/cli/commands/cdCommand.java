package org.cli.commands;
import org.cli.utils.FileSystemManager;


public class cdCommand implements Command{
    @Override
    public String execute(String[] args) {
        try {
            FileSystemManager.getInstance().changeDirectory(args[0]);
            return null;
        } catch (Exception e) {
            return "Cannot find path";
        }
    }
}

