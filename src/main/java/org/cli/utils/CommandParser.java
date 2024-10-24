package org.cli.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    public static List<String> parseCommand(String command) {
        List<String> parsedArgs = new ArrayList<>();

        String regex = "\"([^\"]*)\"|'([^']*)'|\\S+";

        Matcher matcher = Pattern.compile(regex).matcher(command.trim());

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                parsedArgs.add(matcher.group(1));  // Double-quoted paths
            } else if (matcher.group(2) != null) {
                parsedArgs.add(matcher.group(2));  // Single-quoted paths
            } else {
                parsedArgs.add(matcher.group());   // Non-quoted part
            }
        }

        return parsedArgs;
    }
}
