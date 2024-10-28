package org.cli.commands;
import org.cli.commands.enums.CommandType;
import org.cli.commands.enums.OutputDirection;
import org.cli.utils.CommandParser;
import org.cli.utils.CustomFileWriter;
import org.cli.utils.FileSystemManager;

import java.util.List;
import java.util.Scanner;

public class CommandsController {
    private final FileSystemManager fManager ;

    public CommandsController(){
        this.fManager = FileSystemManager.getInstance();
    }

    private void prompt(){
        String s = fManager.getCurrentDirectory() ;
        String hm = fManager.getHomeDirectory().substring(0, fManager.getHomeDirectory().length() - 1);
        if(s.startsWith( hm )){
            int sz = fManager.getHomeDirectory().length() ;
            s = "~" + s.substring(sz-1);
        }
        System.out.printf("%s$ ", s);
    }

    public void Run(){
        Scanner sc = new Scanner(System.in);
        while(true){
            prompt();

            String cmd = sc.nextLine();
            if (cmd.equals("exit")) break;

            List<ParsedCommand> pCmds = CommandParser.parse(cmd) ;

            for(int i=0 ; i < pCmds.size() ; i++){
                ParsedCommand pCmd = pCmds.get(i);

                String result = pCmd.Execute();
                if(pCmd.getcmdType() == CommandType.NON_RETURNABLE){
                    if(pCmd.getexecutionStatus() == ParsedCommand.ExecutionStatus.FAIL){
                        System.out.println(result);
                    }
                    break;
                }
                else{
                    if(pCmd.getexecutionStatus()== ParsedCommand.ExecutionStatus.FAIL){
                        System.out.println(result);
                        break;
                    }
                    switch (pCmd.getOutputDirection()){
                        case OutputDirection.TERMINAL -> System.out.println(result);
                        case OutputDirection.PIPE -> {if( i+1 < pCmds.size()) pCmds.get(i+1).addPipedOutput(result);}
                        case OutputDirection.FILE_APPEND -> {
                            String s = CustomFileWriter.write(pCmd.getFile(), result, true) ;
                            if(s!=null){
                                System.out.println(s);
                            }
                        }
                        case OutputDirection.FILE_OVERWRITE -> {
                            String s = CustomFileWriter.write(pCmd.getFile(), result, false) ;
                            if(s!=null){
                                System.out.println(s);
                            }
                        }
                    }
                }
            }
        }
        sc.close();
    }
}
