package org.cli.commands;
import org.cli.utils.FileSystemManager;

public class pwdCommand implements Command{
    @Override
    public String execute(String[] args){
        return FileSystemManager.getInstance().getCurrentDirectory();
    }
}
