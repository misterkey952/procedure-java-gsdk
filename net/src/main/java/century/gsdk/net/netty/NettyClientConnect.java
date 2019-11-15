package century.gsdk.net.netty;
import century.gsdk.net.NetLogger;
import century.gsdk.net.core.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
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
public class NettyClientConnect implements NetConnect {
    private Identifier identifier;
    private EventLoopGroup workGroup;
    private ChannelInitializer<SocketChannel> channelInitializer;
    private int threadCount;
    private SocketChannel channel;
    private NetAddress remoteAddress;
    private NetAddress localAddress;
    public NettyClientConnect(Identifier identifier,String ip, int port, int threadCount, ChannelInitializer<SocketChannel> channelInitializer) {
        this.identifier=identifier;
        this.remoteAddress=new NetAddress(ip,port);
        this.channelInitializer=channelInitializer;
        this.threadCount=threadCount;
    }


    public NettyClientConnect(Identifier identifier,String ip, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        this.remoteAddress=new NetAddress(ip,port);
        this.channelInitializer=channelInitializer;
        threadCount=1;
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public NetAddress getRemoteAddress() {
        return this.remoteAddress;
    }

    @Override
    public NetAddress getLocalAddress() {
        return this.localAddress;
    }

    @Override
    public boolean connect() {
        try {
            workGroup=new NioEventLoopGroup(threadCount);
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(workGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(channelInitializer);
            channel = (SocketChannel) bootstrap.connect(remoteAddress.getIp(),remoteAddress.getPort()).sync().channel();
            localAddress=new NetAddress(
                    channel.localAddress().getAddress().getHostAddress(),
                    channel.localAddress().getPort()
            );
            channel.closeFuture().addListener(new GenericFutureListener<ChannelFuture>(){
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    shutDownThread();
                }
            });
            NetLogger.NetLog.info("Connect:local {} remote {} success",localAddress.toString(),remoteAddress.toString());
            return true;
        }catch(Exception e) {
            this.close();
            NetLogger.NetLog.error("Connect:"+remoteAddress.toString(),e);
            return false;
        }
    }


    private void shutDownThread(){
        workGroup.shutdownGracefully();
    }

    @Override
    public void close() {
        if(channel!=null){
            channel.close();
        }
        NetLogger.NetLog.info("Connect:{} connect {} close",remoteAddress.toString());
    }
    @Override
    public void sendMsg(Object msg) {
        channel.writeAndFlush(msg);
    }

    @Override
    public void syncSendMsg(Object msg) {
        try {
            channel.writeAndFlush(msg).sync();
        } catch (InterruptedException e) {
            NetLogger.NetLog.error("syncSendMsg err",e);
        }
    }

    @Override
    public void sendMsg(Object msg, NetSendCallBack sendCallBack) {
        channel.writeAndFlush(msg).addListener(new NettySendCallBack(sendCallBack));
    }

    @Override
    public void addCloseHook(NetConnectCloseHook connectCloseHook) {
        connectCloseHook.setConnect(this);
        channel.closeFuture().addListener(new NettyConnectCloseHook(connectCloseHook));
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
