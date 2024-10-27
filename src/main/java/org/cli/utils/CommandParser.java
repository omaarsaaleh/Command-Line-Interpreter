package org.cli.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cli.commands.* ;
import org.cli.utils.pathresolvers.PathResolver;

public class CommandParser {
    private static List<String> tokenize(String command) {
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


    public static List<ParsedCommand> parse(String command, PathResolver resolver) {
        List<String> tokens = CommandParser.tokenize(command);
        List<String> symbols = Arrays.asList(">", "|", ">>");
        List<ParsedCommand> pCmds = new ArrayList<ParsedCommand>() ;



        for(int i=0 ; i<tokens.size() ; i++){
            String cmd = tokens.get(i) ;
            List<String> args = new ArrayList<String>() ;
            List<String> options = new ArrayList<String>() ;

            i++ ;
            while( i<tokens.size() && !symbols.contains( tokens.get(i) )  ){
                String token = tokens.get(i) ;
                if(token.startsWith("-")){
                    options.add(token) ;
                }
                else{
                    args.add(token) ;
                }
                i++ ;
            }
            OutputDirection output = OutputDirection.TERMINAL ;
            String file = null ;
            if(i<tokens.size()){
                switch (tokens.get(i)) {
                    case ">" :
                        output = OutputDirection.FILE_OVERWRITE;
                        file = i+1 < tokens.size() ? resolver.resolve(tokens.get(++i))  : file  ;
                        break;
                    case ">>" :
                        output = OutputDirection.FILE_APPEND;
                        file = i+1 < tokens.size() ? resolver.resolve(tokens.get(++i)) : file ;
                        break;
                    case "|" :
                        output = OutputDirection.PIPE;
                        break;
                };
            }

            pCmds.add(new ParsedCommand(cmd, args, options, output, file)) ;

        }
        return  pCmds ;
    }
}
