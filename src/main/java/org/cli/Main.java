package org.cli;
import org.cli.commands.Command;
import org.cli.commands.cdCommand;
import org.cli.commands.pwdCommand;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Command pwd = new pwdCommand();
        String[] arg = {"xx"};
        Command cd = new cdCommand();


        System.out.println(cd.execute(arg));
        System.out.println(pwd.execute(arg));


    }
}