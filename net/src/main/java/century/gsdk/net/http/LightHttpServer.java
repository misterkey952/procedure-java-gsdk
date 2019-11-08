package century.gsdk.net.http;

import century.gsdk.net.NetLogger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) <2019>  <Century>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * <p>
 * Author's Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class LightHttpServer {
    private Map<String,IHttpHandler> handlerMap=new HashMap<>();
    public void start(String ip,int port){
        EventLoopGroup bossgroup=new NioEventLoopGroup(1);
        try{
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossgroup,bossgroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                            ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("custom",new CustomHandler(handlerMap));
                        }
                    });
            ChannelFuture channelFuture=bootstrap.bind(ip,port).sync();
            NetLogger.LightHttpServer.info("the light http server start success on {}:{}",ip,port);
            channelFuture.channel().closeFuture().addListener(new GenericFutureListener<ChannelFuture>() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    bossgroup.shutdownGracefully();
                    NetLogger.LightHttpServer.info("the light http server close on {}:{}",ip,port);
                }
            });
        }catch(Exception e){
            NetLogger.LightHttpServer.error("start server err",e);
        }
    }


    public void registerHandler(String uri,IHttpHandler handler){
        handlerMap.put(uri,handler);
    }

}
