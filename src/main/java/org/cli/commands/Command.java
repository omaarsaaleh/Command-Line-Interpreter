package org.cli.commands;

import java.io.IOException;

public interface Command {
     String execute(String[] args) ;
     CommandType getCommandType();
     int getNumOfArguments();

     void addOptions(String[] options) ;

}
