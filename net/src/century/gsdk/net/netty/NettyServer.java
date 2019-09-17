package century.gsdk.net.netty;
import century.gsdk.net.core.NetSession;
import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


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
            channelFuture=bootstrap.bind(getIp(),getPort()).sync();
            info("start success");
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
    public void close() {
        if(channelFuture!=null){
            channelFuture.channel().close();
        }
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
        info("close success");
    }

}
