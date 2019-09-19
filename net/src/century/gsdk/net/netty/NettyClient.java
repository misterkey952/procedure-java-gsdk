package century.gsdk.net.netty;
import century.gsdk.net.core.NetSession;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
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
public abstract class NettyClient extends NettyConnect{
    private EventLoopGroup workGroup;
    private Bootstrap bootstrap;
    private NettySession session;
    private int threadCount;
    public NettyClient(String name, String ip, int port) {
        super(name, ip, port);
        threadCount=1;
    }

    public NettyClient(String name, String ip, int port,int threadCount) {
        super(name, ip, port);
        this.threadCount=threadCount;
    }

    @Override
    public void onClose() {
        if(channel!=null){
            channel.close();
        }
        workGroup.shutdownGracefully();
        info("close success");
    }

    @Override
    public void start() {
        try {
            workGroup=new NioEventLoopGroup(threadCount);
            bootstrap=new Bootstrap();
            bootstrap.group(workGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(channelInitializer());
            channel =bootstrap.connect(getIp(), getPort()).sync().channel();
            info("connect success");
            channel.closeFuture().addListener(new NetConnectCloseEvent(this));
        }catch(Exception e) {
            this.close();
            error("connect err",e);
        }
    }


    @Override
    public void registerSession(NetSession session) {
        this.session= (NettySession) session;
    }

    public NetSession session(Object param){
        return this.session;
    }
    public NetSession session(){
        return session(null);
    }


}
