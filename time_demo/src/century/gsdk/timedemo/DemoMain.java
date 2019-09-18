package century.gsdk.timedemo;

import century.gsdk.docker.GameDocker;

import java.io.File;

public class DemoMain {
    public static void main(String[] args){
        GameDocker docker=new GameDocker(DemoApplication.getInstance());
        docker.start(System.getProperty("user.dir")+ File.separator+"time_demo","DEMOTIME");
    }
}
