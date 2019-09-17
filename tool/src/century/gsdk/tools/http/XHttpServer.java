package century.gsdk.tools.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

public class XHttpServer {
	private static Server server;
	private static ContextHandler context;
	public static void init(XHttpConfig config){
		server=new Server(config.getPort());
		context = new ContextHandler();
		context.setResourceBase(".");
		context.setClassLoader(Thread.currentThread()  
                .getContextClassLoader());
		 server.setHandler(context);
	}
	
	public static void registUrl(String url,AbstractHandler handler){
		context.setContextPath(url);
		context.setHandler(handler);
	}
	
	public static void startHttpServer(){
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
