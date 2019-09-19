package century.gsdk.net.netty;
import century.gsdk.net.core.NetSession;
import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public abstract class NettyServer extends NettyConnect{
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ServerBootstrap bootstrap;
    private Map<Object,NettySession> nettySessionMap;
    private int bossThreadCount;
    private int workThreadCount;
    public NettyServer(String name, String ip, int port) {
        super(name, ip, port);
        nettySessionMap=new ConcurrentHashMap<>();
        bossThreadCount=0;
        workThreadCount=0;
    }

    public NettyServer(String name, String ip, int port,int bossThreadCount,int workThreadCount) {
        super(name, ip, port);
        nettySessionMap=new ConcurrentHashMap<>();
        this.bossThreadCount=bossThreadCount;
        this.workThreadCount=workThreadCount;
    }

    public void start(){
        try {
            bootstrap=new ServerBootstrap();
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
                    .childHandler(channelInitializer());
            channel=bootstrap.bind(getIp(),getPort()).sync().channel();
            info("start success");
            channel.closeFuture().addListener(new NetConnectCloseEvent(this));
        }catch(Exception e) {
            this.close();
            error("start err",e);
        }
    }


    @Override
    public void registerSession(NetSession session) {
        NettySession nettySession= (NettySession) session;
        nettySessionMap.put(nettySession.socketChannel(),nettySession);
    }

    public NetSession session(Object socketChannel){
        return nettySessionMap.get(socketChannel);
    }

    @Override
    public void onClose() {
        if(channel!=null){
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
        info("close success");
    }

}
