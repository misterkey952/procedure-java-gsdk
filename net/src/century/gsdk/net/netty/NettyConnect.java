package century.gsdk.net.netty;
import century.gsdk.net.core.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class NettyConnect implements NetConnect {
    private static final Logger logger= LoggerFactory.getLogger("NettyConnect");
    private Identifier identifier;
    private EventLoopGroup workGroup;
    private ChannelInitializer<SocketChannel> channelInitializer;
    private int threadCount;
    protected SocketChannel channel;
    private NetAddress remoteAddress;
    private NetAddress localAddress;

    public NettyConnect(Identifier identifier,String ip,int port,int threadCount,ChannelInitializer<SocketChannel> channelInitializer) {
        this.identifier=identifier;
        this.remoteAddress=new NetAddress(ip,port);
        this.channelInitializer=channelInitializer;
        this.threadCount=threadCount;
    }



    public NettyConnect(Identifier identifier,String ip,int port,ChannelInitializer<SocketChannel> channelInitializer) {
        this.identifier=identifier;
        this.remoteAddress=new NetAddress(ip,port);
        this.channelInitializer=channelInitializer;
        threadCount=1;
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
            logger.info("Connect:{} connect local {} remote {} success",identifier.toString(),localAddress.toString(),remoteAddress.toString());
            return true;
        }catch(Exception e) {
            this.close();
            logger.error("Connect:"+identifier.toString()+" connect "+remoteAddress.toString(),e);
            return false;
        }
    }


    void shutDownThread(){
        workGroup.shutdownGracefully();
    }

    @Override
    public void close() {
        if(channel!=null){
            channel.close();
        }
        logger.info("Connect:{} connect {} close",identifier.toString(),remoteAddress.toString());
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
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
            logger.error("syncSendMsg err",e);
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

}
