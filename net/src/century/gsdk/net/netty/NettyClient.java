package century.gsdk.net.netty;
import century.gsdk.net.core.NetSession;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

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
