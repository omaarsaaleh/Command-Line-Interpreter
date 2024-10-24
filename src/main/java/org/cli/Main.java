package org.cli;
import org.cli.commands.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Command cd = new cdCommand();
        String[] arg = {"/home/mostafa/elgyar"};
        System.out.println(cd.execute(arg));
        Command pwd = new pwdCommand();
        System.out.println(pwd.execute(arg));
        String[] n = {"GGGeeeesssssss"};
        Command dk = new rmdirCommand();
        dk.execute(n);

    }
}