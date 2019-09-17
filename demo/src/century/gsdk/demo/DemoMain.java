package century.gsdk.demo;

import century.gsdk.docker.GameDocker;

import java.io.File;

public class DemoMain {
    public static void main(String[] args){
        GameDocker docker=new GameDocker(new DemoApplication());
        docker.start(System.getProperty("user.dir")+ File.separator+"demo","DemoApp");
    }
}
