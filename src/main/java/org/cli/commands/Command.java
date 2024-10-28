package org.cli.commands;

import org.cli.commands.enums.CommandType;

public interface Command {
     String execute(String[] args) ;
     CommandType getCommandType();
     int getNumOfArguments();

     void addOptions(String[] options) ;

}
