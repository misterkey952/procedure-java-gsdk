package century.gsdk.ts;
import century.gsdk.docker.GameDocker;
import century.gsdk.ts.objs.TimeApplication;

import java.io.File;

public class TimeMain {
    public static void main(String[] args){
        GameDocker docker=new GameDocker(TimeApplication.getInstance());
        docker.start(System.getProperty("user.dir")+ File.separator+"time_server","TimeServer");
    }
}
