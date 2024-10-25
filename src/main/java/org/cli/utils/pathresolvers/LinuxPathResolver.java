package org.cli.utils.pathresolvers;
import org.cli.utils.FileSystemManager;


import java.nio.file.Paths;

public class LinuxPathResolver implements PathResolver{
    private static LinuxPathResolver instance;

    public static PathResolver getInstance(){
        if (instance == null){
            instance = new LinuxPathResolver();
        }
        return instance;

    }

    @Override
    public String resolve(String path) {

        if(path.startsWith("~")){
            path = FileSystemManager.getInstance().getHomeDirectory() + "/" + path.substring(1) ;
        }
        else if (!path.startsWith("/")) {
            path = FileSystemManager.getInstance().getCurrentDirectory() + "/" + path ;
        }

        return Paths.get(path).normalize().toString();

    }


}
