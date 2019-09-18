package century.gsdk.ts.objs;

import century.gsdk.docker.GameApplication;
import century.gsdk.ts.net.TimeServer;

public class TimeApplication extends GameApplication {
    private static final TimeApplication instance=new TimeApplication();
    private TimeApplication(){}
    private TimeServer timeServer;

    public static TimeApplication getInstance(){
        return instance;
    }
    @Override
    public void initialize() {
        timeServer=new TimeServer("TIMES","127.0.0.1",6000);
        timeServer.start();
        GTime gTime= GTimeManager.getInstance().createGTime(1);
        gTime.start();
    }
}
