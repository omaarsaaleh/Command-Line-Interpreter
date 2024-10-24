package org.cli.commands;

public class pwdCommand implements Command{
    @Override
    public String execute(String[] args){
        return System.getProperty("user.dir") ;
    }
}
