package http;

import century.gsdk.docker.GameApplication;
import century.gsdk.net.http.LightHttpServer;

/**
 * Author:Century,Write on 2019/11/7
 * Description:
 */
public class HttpApplication extends GameApplication {
    private static final HttpApplication instance=new HttpApplication();
    public static HttpApplication getInstance(){
        return instance;
    }
    private LightHttpServer server;
    @Override
    protected void initialize() {
        server=new LightHttpServer();
        server.start("127.0.0.1",8080);
    }
}
