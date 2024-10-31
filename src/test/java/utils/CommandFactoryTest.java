package utils;
import static org.junit.jupiter.api.Assertions.*;

import org.cli.commands.*;
import org.cli.commands.CommandFactory;
import org.junit.jupiter.api.Test;

public class CommandFactoryTest {
    @Test
    void CatTest(){
        Command catCommand = CommandFactory.getCommand("cat");
        assertInstanceOf(catCommand.class, catCommand);
    }
    @Test
    void cdTest(){
        Command cdCommand = CommandFactory.getCommand("cd");
        assertInstanceOf(cdCommand.class, cdCommand);
    }
    @Test
    void lsTest(){
        Command lsCommand = CommandFactory.getCommand("ls");
        assertInstanceOf(lsCommand.class, lsCommand);
    }
    @Test
    void mvTest(){
        Command mvCommand = CommandFactory.getCommand("mv");
        assertInstanceOf(mvCommand.class, mvCommand);
    }
    @Test
    void pwdTest(){
        Command pwdCommand = CommandFactory.getCommand("pwd");
        assertInstanceOf(pwdCommand.class, pwdCommand);
    }
    @Test
    void rmTest(){
        Command rmCommand = CommandFactory.getCommand("rm");
        assertInstanceOf(rmCommand.class, rmCommand);
    }
    @Test
    void mkdirTest(){
        Command mkdirCommand = CommandFactory.getCommand("mkdir");
        assertInstanceOf(mkdirCommand.class, mkdirCommand);
    }

}
