package org.cli;
import org.cli.commands.*;
import org.cli.utils.pathresolvers.*;


public class Main {
    public static void main(String[] args) {

        PathResolver resolver = PathResolverFactory.getResolver() ;
        CommandsController d = new CommandsController(resolver);
        d.Run();

    }
}

