package org.cli;
import org.cli.commands.*;
import org.cli.utils.pathresolvers.*;


public class Main {
    public static void main(String[] args) {

        PathResolver resolver = LinuxPathResolver.getInstance();
        CommandsController d = new CommandsController(resolver);
        d.Run();

    }
}

