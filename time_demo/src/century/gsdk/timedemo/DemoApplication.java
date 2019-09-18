package century.gsdk.timedemo;

import century.gsdk.docker.GameApplication;

public class DemoApplication extends GameApplication {
    private static final DemoApplication instance=new DemoApplication();
    private DemoApplication(){}
    public static DemoApplication getInstance(){
        return instance;
    }
    private DemoClient demoClient;
    @Override
    public void initialize() {
        demoClient=new DemoClient("DEMOCLIENT","127.0.0.1",6000);
        demoClient.start();
        demoClient.register();
    }
}
