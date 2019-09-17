package century.gsdk.docker;

import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;

public class GameDocker {
    private GameApplication application;

    public GameDocker(GameApplication application) {
        this.application = application;
    }

    public void start(String appRootPath,String appName){
        DOMConfigurator.configure(appRootPath+ File.separator+"cfg"+ File.separator +"log4j.xml");
        application.init(appRootPath,appName);
    }
}
