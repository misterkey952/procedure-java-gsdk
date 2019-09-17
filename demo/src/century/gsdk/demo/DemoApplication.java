package century.gsdk.demo;

import century.gsdk.docker.GameApplication;

public class DemoApplication extends GameApplication {
    @Override
    public void initialize() {
        System.out.println("111111111111");
        info("this is demo start {}",System.currentTimeMillis());
    }
}
