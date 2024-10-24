package org.cli;
import org.cli.commands.pwdCommand;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        pwdCommand pwd = new pwdCommand();
        String[] arg = {};
        System.out.println(pwd.execute(arg));


    }
}