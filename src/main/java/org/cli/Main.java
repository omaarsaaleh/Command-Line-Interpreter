package org.cli;
import org.cli.commands.*;
import org.cli.utils.pathresolvers.*;

import javax.naming.spi.Resolver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        PathResolver resolver = LinuxPathResolver.getInstance();
        Command hlp = new helpCommand();
        hlp.execute(new String[]{"hello"});

        /*
        Stream<Path> files = Files.list(Paths.get("/home/omar/Documents/"));
        files.forEach(file -> System.out.println(file.getFileName()));

        * */


    }
}

/*
* valid file to valid folder
*
* invalid file to valid folder
* valid file to existing file
* valid file to non existing
*
* */