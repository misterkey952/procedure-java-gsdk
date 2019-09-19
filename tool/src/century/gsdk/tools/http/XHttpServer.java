package century.gsdk.tools.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
/**
 *     Copyright (C) <2019>  <Century>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
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
