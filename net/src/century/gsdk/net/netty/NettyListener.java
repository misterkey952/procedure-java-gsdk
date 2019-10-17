package century.gsdk.net.netty;

import century.gsdk.net.core.Identifier;
import century.gsdk.net.core.NetAddress;
import century.gsdk.net.core.NetListener;
import century.gsdk.net.core.NetListenerCloseHook;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class NettyListener implements NetListener {
    private static final Logger logger= LoggerFactory.getLogger("NettyListener");
    private Identifier identifier;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private int bossThreadCount;
    private int workThreadCount;
    private ChannelInitializer<SocketChannel> channelInitializer;
    private NetAddress listenAddress;
    protected Channel channel;

    public NettyListener(Identifier identifier, String ip, int port, int bossThreadCount,int workThreadCount,ChannelInitializer<SocketChannel> channelInitializer) {
        this.channelInitializer=channelInitializer;
        this.bossThreadCount=bossThreadCount;
        this.workThreadCount=bossThreadCount;
        this.identifier=identifier;
        listenAddress=new NetAddress(ip,port);
    }


    public NettyListener(Identifier identifier, String ip, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        this.channelInitializer=channelInitializer;
        bossThreadCount=1;
        workThreadCount=1;
        this.identifier=identifier;
        listenAddress=new NetAddress(ip,port);
    }
    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public NetAddress getListenNetAddress() {
        return listenAddress;
    }

    @Override
    public void listen() {
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            if(bossThreadCount>0){
                bossGroup=new NioEventLoopGroup(bossThreadCount);
            }else{
                bossGroup=new NioEventLoopGroup();
            }

            if(workThreadCount>0){
                workGroup=new NioEventLoopGroup(workThreadCount);
            }else{
                workGroup=new NioEventLoopGroup();
            }

            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(channelInitializer);
            channel=bootstrap.bind(listenAddress.getIp(),listenAddress.getPort()).sync().channel();
            logger.info("Service:{} listen on {} success",identifier.toString(),listenAddress.toString());
        }catch(Exception e) {
            this.close();
            logger.error("Service:"+identifier.toString()+" listen on "+listenAddress.toString()+" err",e);
        }
    }

    void shutDownThread(){
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    @Override
    public void close() {
        if(channel!=null){
            channel.close();
        }
        logger.info("Service:{} listen on {} close",identifier.toString(),listenAddress.toString());
    }

    @Override
    public void addCloseHook(NetListenerCloseHook listenerCloseHook) {
        listenerCloseHook.setListener(this);
        channel.closeFuture().addListener(new NettyListenerCloseHook(listenerCloseHook));
        logger.info("Service:{} listen on {} addCloseHook [{}]",identifier.toString(),listenAddress.toString(),listenerCloseHook.getClass().getName());
    }
}
